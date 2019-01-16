package com.agh.hydra.referral

import com.agh.hydra.api.register.service.IPrivilegeService
import com.agh.hydra.common.model.*
import com.agh.hydra.job.model.JobAnnouncement
import com.agh.hydra.job.request.JobAnnouncementFilterRequest
import com.agh.hydra.job.service.IJobService
import com.agh.hydra.referral.dao.ReferralRepository
import com.agh.hydra.referral.entity.ReferralDetailsEntity
import com.agh.hydra.referral.entity.ReferralEntity
import com.agh.hydra.referral.impl.ReferralService
import com.agh.hydra.referral.model.ReferralAnnouncementFilter
import com.agh.hydra.referral.model.ReferralId
import com.agh.hydra.referral.request.CreateReferralRequest
import com.agh.hydra.referral.request.ReferralAnnouncementFilterRequest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.web.server.ResponseStatusException
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Instant

import static com.agh.hydra.common.exception.BusinessException.ACTIVE_REFERRAL_EXISTS
import static com.agh.hydra.common.exception.BusinessException.INVALID_REFERRAL_CLOSING_DATE
import static com.agh.hydra.common.model.FunctionalPrivilege.FN_PRV_CREATE_REFERRAL
import static com.agh.hydra.common.util.CollectionUtils.haveSameElements
import static com.agh.hydra.common.util.ValueObjectUtil.getValue
import static java.time.temporal.ChronoUnit.DAYS
import static java.util.Collections.*
import static org.spockframework.util.CollectionUtil.asSet
import static org.springframework.http.HttpStatus.BAD_REQUEST

class ReferralServiceSpec extends Specification {

    def privilegeService = Mock(IPrivilegeService)
    def referralRepository = Mock(ReferralRepository)
    def jobService = Mock(IJobService)

    def service = new ReferralService(privilegeService, referralRepository, jobService)

    @Shared def testUserId = UserId.of("1")
    @Shared def testJobId = JobId.of(2115)
    @Shared def testCity = "Bengal"
    @Shared def testDescription = InformationContent.of("referral description")
    @Shared def referralBonus = 10000L
    @Shared def testBonusPercentage = 0.70
    @Shared def testClosingDate = Instant.now()

    @Unroll
    def "createReferralAnnouncement() - happy path"() {
        given:
        def request = buildCreateRequest(closingDate)

        def job = job(testJobId, testClosingDate.plus(2, DAYS))

        and:
        1 * privilegeService.throwIfUnprivileged(testUserId, FN_PRV_CREATE_REFERRAL)
        1 * jobService.getJobAnnouncements(_ as JobAnnouncementFilterRequest, PageRequest.of(0, 1)) >> {
            JobAnnouncementFilterRequest filter, Pageable pageable ->
                assert filter.includeIds.every({ it == testJobId })

                new PageImpl<>(singletonList(job), pageable, 1)
        }
        1 * referralRepository.referralAnnouncementExists(testUserId.value, testJobId.value) >> false
        1 * referralRepository.createReferralAnnouncement(_ as ReferralEntity) >> {
            ReferralEntity entity ->
                assert entity.jobId == testJobId.value
                assert entity.description == testDescription.value
                assert entity.referralBonus == referralBonus
                assert entity.referralBonusPercentage == testBonusPercentage
                assert entity.closingDate == closingDate
        }

        when:
        service.createReferralAnnouncement(request, testUserId)

        then:
        noExceptionThrown()

        where:
        closingDate                     | _
        testClosingDate                 | _
        testClosingDate.minus(2, DAYS)  | _
        testClosingDate.minus(3, DAYS)  | _
        testClosingDate.minus(12, DAYS) | _
    }

    @Unroll
    def "createReferralAnnouncement() - unhappy path"() {
        given:
        def request = buildCreateRequest(closingDate)

        and:
        1 * privilegeService.throwIfUnprivileged(testUserId, FN_PRV_CREATE_REFERRAL)
        1 * jobService.getJobAnnouncements(_ as JobAnnouncementFilterRequest, PageRequest.of(0, 1)) >> {
            JobAnnouncementFilterRequest filter, Pageable pageable ->
                assert filter.includeIds.every({ it == testJobId })

                new PageImpl<>(job ? singletonList(job) : emptyList(), pageable, job ? 1 : 0)
        }
        (0..1) * referralRepository.referralAnnouncementExists(testUserId.value, testJobId.value) >> referralExists
        0 * referralRepository.createReferralAnnouncement(_ as ReferralEntity)

        when:
        service.createReferralAnnouncement(request, testUserId)

        then:
        def exception = thrown(ResponseStatusException.class)
        assert exception.status == BAD_REQUEST
        assert exception.reason == errorMessage

        where:
        job       | closingDate                    | referralExists || errorMessage
        testJob() | testClosingDate                | true           || ACTIVE_REFERRAL_EXISTS.message
        testJob() | testClosingDate                | true           || ACTIVE_REFERRAL_EXISTS.message
        testJob() | testClosingDate.plus(3, DAYS)  | false          || INVALID_REFERRAL_CLOSING_DATE.message
        testJob() | testClosingDate.plus(12, DAYS) | false          || INVALID_REFERRAL_CLOSING_DATE.message
        null      | testClosingDate.minus(3, DAYS) | false          || "Job announcement '" + testJobId + "' does not exist"
        null      | testClosingDate.plus(12, DAYS) | true           || "Job announcement '" + testJobId + "' does not exist"
    }

    @Unroll
    def "getReferralAnnouncement() - happy path"() {
        given:
        def pageable = PageRequest.of(0, 10)
        def request = [
                includeIds: includeIds.collect { ReferralId.of(it) },
                companyIds: companyIds.collect { CompanyId.of(it) },
                jobIds    : jobIds.collect { JobId.of(it) },
                cities    : cities
        ] as ReferralAnnouncementFilterRequest

        and:
        def repoContent = referralEntities(repoSize)
        def repoResult = mockReferralRepository(repoContent, request)

        and:
        0 * privilegeService.throwIfUnprivileged(_ as UserId, _ as FunctionalPrivilege)
        1 * referralRepository.getReferralAnnouncement(_ as ReferralAnnouncementFilter) >> {
            ReferralAnnouncementFilter filter ->
                assertReferralFilter(filter, includeIds, companyIds, cities, jobIds, pageable)
                repoResult
        }
        1 * referralRepository.getReferralAnnouncementCount(_ as ReferralAnnouncementFilter) >> {
            ReferralAnnouncementFilter filter ->
                assertReferralFilter(filter, includeIds, companyIds, cities, jobIds, pageable)
                repoResult.size()
        }

        when:
        def result = service.getReferralAnnouncement(request, pageable)

        then:
        assert result.number == pageable.getOffset()
        assert result.size == pageable.getPageSize()
        assert result.totalPages == (repoSize > 0 ? 1 : 0)
        assert result.numberOfElements == expectedIds.size()
        assert result.totalElements == expectedIds.size()
        assert haveSameElements(result.content.collect { getValue(it.referralId) }, expectedIds)


        where:
        includeIds        | companyIds              | cities              | jobIds                     | repoSize || expectedIds
        asSet(1L, 2L, 3L) | asSet("c1", "c2", "c3") | asSet(testCity)     | singleton(testJobId.value) | 5        || asSet(1L, 2L, 3L)
        null              | asSet("c1", "c3")       | singleton(testCity) | null                       | 5        || asSet(1L, 3L)
        asSet(1L, 2L, 3L) | null                    | null                | singleton(testJobId.value) | 5        || asSet(1L, 2L, 3L)
        null              | null                    | null                | singleton(testJobId.value) | 2        || asSet(1L, 2L)
    }

    void assertReferralFilter(ReferralAnnouncementFilter filter, Set<Long> includeIds, Set<String> companyIds,
                              Set<String> cities, Set<Long> jobIds, Pageable pageable) {
        assert jobIds ? filter.jobIds == jobIds : true
        assert cities ? filter.cities == cities : true
        assert companyIds ? filter.companyIds == companyIds : true
        assert includeIds ? filter.includeIds == includeIds : true
        assert filter.offset == pageable.getOffset()
        assert filter.pageSize == pageable.getPageSize()
    }

    def mockReferralRepository(List<ReferralDetailsEntity> entities, ReferralAnnouncementFilterRequest request) {
        entities.findAll {
            (request.includeIds ? request.includeIds.collect { it.value }.contains(it.referralId) : true) &&
                    (request.jobIds ? request.jobIds.collect { it.value }.contains(it.jobId) : true) &&
                    (request.companyIds ? request.companyIds.collect { it.value }.contains(it.companyId) : true) &&
                    (request.cities ? request.cities.contains(it.city) : true)
        } as List<ReferralDetailsEntity>
    }

    def referralEntities(long size) {
        size > 0 ? (1..size).collect {
            [referralId: it,
             jobId     : testJobId.value,
             companyId : "c" + it,
             authorId  : testUserId.value,
             city      : testCity
            ] as ReferralDetailsEntity
        } : []
    }

    def testJob() {
        job(testJobId, Instant.now().plus(2, DAYS))
    }

    def job(JobId id, Instant closingDate) {
        [
                jobId      : id,
                closingDate: closingDate
        ] as JobAnnouncement
    }

    def buildCreateRequest(Instant closingDate) {
        [
                jobId                  : testJobId,
                description            : testDescription,
                referralBonus          : referralBonus,
                referralBonusPercentage: testBonusPercentage,
                closingDate            : closingDate
        ] as CreateReferralRequest
    }
}
