package com.agh.hydra.referral

import com.agh.hydra.api.register.service.IPrivilegeService
import com.agh.hydra.common.model.InformationContent
import com.agh.hydra.common.model.JobId
import com.agh.hydra.common.model.UserId
import com.agh.hydra.job.model.JobAnnouncement
import com.agh.hydra.job.request.JobAnnouncementFilterRequest
import com.agh.hydra.job.service.IJobService
import com.agh.hydra.referral.dao.ReferralRepository
import com.agh.hydra.referral.entity.ReferralEntity
import com.agh.hydra.referral.impl.ReferralService
import com.agh.hydra.referral.request.CreateReferralRequest
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
import static java.time.temporal.ChronoUnit.DAYS
import static java.util.Collections.emptyList
import static java.util.Collections.singletonList
import static org.springframework.http.HttpStatus.BAD_REQUEST

class ReferralServiceSpec extends Specification {

    def privilegeService = Mock(IPrivilegeService)
    def referralRepository = Mock(ReferralRepository)
    def jobService = Mock(IJobService)

    def service = new ReferralService(privilegeService, referralRepository, jobService)

    @Shared def testUserId = UserId.of("1")
    @Shared def testJobId = JobId.of(2115)
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
