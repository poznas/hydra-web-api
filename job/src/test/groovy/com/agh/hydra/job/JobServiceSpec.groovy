package com.agh.hydra.job

import com.agh.hydra.api.register.service.IPrivilegeService
import com.agh.hydra.common.model.*
import com.agh.hydra.job.dao.JobRepository
import com.agh.hydra.job.entity.JobEntity
import com.agh.hydra.job.impl.JobService
import com.agh.hydra.job.model.JobAnnouncementFilter
import com.agh.hydra.job.request.CreateJobAnnouncementRequest
import com.agh.hydra.job.request.JobAnnouncementFilterRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Instant

import static com.agh.hydra.common.model.FunctionalPrivilege.FN_PRV_EDIT_JOBS
import static com.agh.hydra.common.model.ProgrammingLanguage.*
import static com.agh.hydra.common.util.CollectionUtils.haveSameElements
import static com.agh.hydra.common.util.ValueObjectUtil.getValue
import static java.util.Collections.singleton
import static org.spockframework.util.CollectionUtil.asSet

class JobServiceSpec extends Specification {

    def jobRepository = Mock(JobRepository)
    def privilegeService = Mock(IPrivilegeService)

    def service = new JobService(jobRepository, privilegeService)

    @Shared def testUserId = UserId.of("1")
    @Shared def testCompanyId = CompanyId.of("@company")
    @Shared def testJobTitle = JobTitle.of("Ada Developer")
    @Shared def testCity = "Bengal"
    @Shared def testDescription = InformationContent.of("job description")
    @Shared def testMinSalary = 10000L
    @Shared def testMaxSalary = 15000L
    @Shared def testClosingDate = Instant.now()

    @Unroll
    def "createJobAnnouncement() - happy path"() {
        given:
        def request = [
                title               : testJobTitle,
                companyId           : testCompanyId,
                city                : testCity,
                description         : testDescription,
                programmingLanguages: inputProgrammingLanguages,
                minSalary           : testMinSalary,
                maxSalary           : testMaxSalary,
                closingDate         : testClosingDate
        ] as CreateJobAnnouncementRequest

        and:
        1 * privilegeService.throwIfUnprivileged(testUserId, FN_PRV_EDIT_JOBS)
        1 * jobRepository.createJobAnnouncement(_ as JobEntity) >> {
            JobEntity entity ->
                assert entity.title == testJobTitle.value
                assert entity.companyId == testCompanyId.value
                assert entity.city == testCity
                assert entity.description == testDescription.value
                assert entity.programmingLanguages == expectedLanguagesString
                assert entity.minSalary == testMinSalary
                assert entity.maxSalary == testMaxSalary
                assert entity.closingDate == testClosingDate
        }
        when:
        service.createJobAnnouncement(request, testUserId)

        then:
        noExceptionThrown()

        where:
        inputProgrammingLanguages || expectedLanguagesString
        [CPP, C, PYTHON, C_SHARP] || "CPP,C,PYTHON,C_SHARP"
        [JAVA, JAVASCRIPT]        || "JAVA,JAVASCRIPT"
        [PHP, SCALA, RUBY]        || "PHP,SCALA,RUBY"
        [OBJECTIVE_C]             || "OBJECTIVE_C"
    }

    @Unroll
    def "getJobAnnouncements() - happy path"() {
        given:
        def pageable = PageRequest.of(0, 10)
        def request = [
                includeIds : includeIds.collect {JobId.of(it)},
                companyIds : companyIds.collect {CompanyId.of(it)},
                languages : languages,
                cities : cities
        ] as JobAnnouncementFilterRequest

        and:
        def repoContent = jobEntities(repoSize)
        def repoResult = mockJobRepository(repoContent, request)

        and:
        0 * privilegeService.throwIfUnprivileged(_ as UserId, _ as FunctionalPrivilege)
        1 * jobRepository.getJobAnnouncements(_ as JobAnnouncementFilter) >> {
            JobAnnouncementFilter filter ->
                assertJobFilter(filter, includeIds, companyIds, cities, languages, pageable)
                repoResult
        }
        1 * jobRepository.getJobAnnouncementCount(_ as JobAnnouncementFilter) >> {
            JobAnnouncementFilter filter ->
                assertJobFilter(filter, includeIds, companyIds, cities, languages, pageable)
                repoResult.size()
        }

        when:
        def result = service.getJobAnnouncements(request, pageable)

        then:
        assert result.number == pageable.getOffset()
        assert result.size == pageable.getPageSize()
        assert result.totalPages == (repoSize > 0 ? 1 : 0)
        assert result.numberOfElements == expectedIds.size()
        assert result.totalElements == expectedIds.size()
        assert haveSameElements(result.content.collect {getValue(it.jobId)}, expectedIds)

        where:
        includeIds        | companyIds              | cities              | languages      | repoSize || expectedIds
        asSet(1L, 2L, 3L) | asSet("c1", "c2", "c3") | asSet(testCity)     | singleton(CPP) | 5        || asSet(1L, 2L, 3L)
        null              | asSet("c1", "c3")       | singleton(testCity) | null           | 5        || asSet(1L, 3L)
        asSet(1L, 2L, 3L) | null                    | null                | singleton(CPP) | 5        || asSet(1L, 2L, 3L)
        null              | null                    | null                | singleton(CPP) | 2        || asSet(1L, 2L)
    }

    void assertJobFilter(JobAnnouncementFilter filter, Set<Long> includeIds, Set<String> companyIds,
                                     Set<String> cities, Set<ProgrammingLanguage> languages, Pageable pageable) {
        assert languages ? filter.languages == languages : true
        assert cities ? filter.cities == cities : true
        assert companyIds ? filter.companyIds == companyIds : true
        assert includeIds ? filter.includeIds == includeIds : true
        assert filter.offset == pageable.getOffset()
        assert filter.pageSize == pageable.getPageSize()
    }

    def mockJobRepository(List<JobEntity> entities, JobAnnouncementFilterRequest request) {
        entities.findAll {
            (request.includeIds ? request.includeIds.collect { it.value }.contains(it.jobId) : true) &&
            (request.companyIds ? request.companyIds.collect { it.value }.contains(it.companyId) : true) &&
            (request.cities ? request.cities.contains(it.city) : true) &&
            (request.languages ? request.languages.contains(CPP) : true)
        } as List<JobEntity>
    }

    def jobEntities(long size) {
        size > 0 ? (1..size).collect {
            [jobId               : it,
             companyId           : "c" + it,
             programmingLanguages: "CPP",
             city                : testCity
            ] as JobEntity
        } : []
    }
}
