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
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Instant

import static com.agh.hydra.common.model.FunctionalPrivilege.FN_PRV_CREATE_REFERRAL
import static java.time.temporal.ChronoUnit.DAYS
import static java.util.Collections.singletonList

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
        def request = [
                jobId                  : testJobId,
                description            : testDescription,
                referralBonus          : referralBonus,
                referralBonusPercentage: testBonusPercentage,
                closingDate            : closingDate
        ] as CreateReferralRequest

        def job = job(testJobId, Instant.now().plus(2, DAYS))

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


    def job(JobId id, Instant closingDate) {
        [
                jobId      : id,
                closingDate: closingDate
        ] as JobAnnouncement


    }
}
