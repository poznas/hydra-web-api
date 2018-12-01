package com.agh.hydra.wiki

import com.agh.hydra.api.register.service.IPrivilegeService
import com.agh.hydra.common.model.*
import com.agh.hydra.wiki.dao.WikiRepository
import com.agh.hydra.wiki.entity.RecruitmentInfoDetailsEntity
import com.agh.hydra.wiki.entity.RecruitmentInfoEntity
import com.agh.hydra.wiki.entity.VoteEntity
import com.agh.hydra.wiki.impl.WikiService
import com.agh.hydra.wiki.model.RecruitmentInfoFilter
import com.agh.hydra.wiki.model.Vote
import com.agh.hydra.wiki.request.CreateRecruitmentInfoRequest
import com.agh.hydra.wiki.request.RecruitmentInformationFilterRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static com.agh.hydra.common.model.FunctionalPrivilege.FN_PRV_CREATE_INFORMATION
import static com.agh.hydra.common.model.ProgrammingLanguage.CPP
import static com.agh.hydra.common.model.RecruitmentType.INTERVIEW
import static com.agh.hydra.common.util.CollectionUtils.haveSameElements
import static com.agh.hydra.common.util.ValueObjectUtil.getValue
import static java.util.Collections.singleton
import static org.spockframework.util.CollectionUtil.asSet

class WikiServiceSpec extends Specification {

    def wikiRepository = Mock(WikiRepository)
    def privilegeService = Mock(IPrivilegeService)

    def service = new WikiService(wikiRepository, privilegeService)

    @Shared def testUserId = UserId.of("1")
    @Shared def testCompanyId = CompanyId.of("@company")

    def "createRecruitmentInformation() - happy path"() {
        given:
        def request = [
                companyId          : testCompanyId,
                recruitmentType    : INTERVIEW,
                content            : InformationContent.of("What is C++?"),
                programmingLanguage: CPP
        ] as CreateRecruitmentInfoRequest

        and:
        1 * privilegeService.throwIfUnprivileged(testUserId, FN_PRV_CREATE_INFORMATION)
        1 * wikiRepository.createInformation(_ as RecruitmentInfoEntity) >> {
            RecruitmentInfoEntity entity ->
                assert entity.companyId == request.companyId.value
                assert entity.content == request.content.value
                assert entity.programmingLanguage == request.programmingLanguage
                assert entity.recruitmentType == request.recruitmentType
                assert entity.authorId == testUserId.value
        }

        when:
        service.createRecruitmentInformation(request, testUserId)

        then:
        noExceptionThrown()
    }

    @Unroll
    def "getRecruitmentInformation() - should return paged content"() {
        given:
        def pageable = PageRequest.of(0, 10)
        def request = [
                includeIds : includeIds.collect {InformationId.of(it)},
                companyIds : companyIds.collect {CompanyId.of(it)},
                languages : languages,
                types : types
        ] as RecruitmentInformationFilterRequest

        and:
        def repoContent = informationEntities(repoSize)
        def repoResult = mockWikiRepository(repoContent, request)
        def expectedInfoIds = repoResult.collect {it.id}
        def userVotes = [[informationId : 1L, vote : Vote.DOWN] as VoteEntity] as List

        and:
        0 * privilegeService.throwIfUnprivileged(_ as UserId, _ as FunctionalPrivilege)
        1 * wikiRepository.getRecruitmentInformation(_ as RecruitmentInfoFilter) >> {
            RecruitmentInfoFilter filter ->
                assertRecruitmentInfoFilter(filter, includeIds, companyIds, types, languages, pageable)
                repoResult
        }
        1 * wikiRepository.getInformationCount(_ as RecruitmentInfoFilter) >> {
            RecruitmentInfoFilter filter ->
                assertRecruitmentInfoFilter(filter, includeIds, companyIds, types, languages, pageable)
                repoResult.size()
        }
        1 * wikiRepository.getUserVotes(getValue(testUserId), expectedInfoIds) >> userVotes

        when:
        def result = service.getRecruitmentInformation(request, pageable, testUserId)

        then:
        assert result.number == pageable.getOffset()
        assert result.size == pageable.getPageSize()
        assert result.totalPages == (repoSize > 0 ? 1 : 0)
        assert result.numberOfElements == expectedIds.size()
        assert result.totalElements == expectedIds.size()
        assert haveSameElements(result.content.collect {getValue(it.id)}, expectedIds)
        assert result.content.find {it.id.value == 1L}.userVote == Vote.DOWN

        where:
        includeIds        | companyIds              | types                | languages      | repoSize || expectedIds
        asSet(1L, 2L, 3L) | asSet("c1", "c2", "c3") | singleton(INTERVIEW) | singleton(CPP) | 5        || asSet(1L, 2L, 3L)
        null              | asSet("c1", "c3")       | singleton(INTERVIEW) | null           | 5        || asSet(1L, 3L)
        asSet(1L, 2L, 3L) | null                    | null                 | singleton(CPP) | 5        || asSet(1L, 2L, 3L)
        null              | null                    | null                 | singleton(CPP) | 2        || asSet(1L, 2L)
    }

    void assertRecruitmentInfoFilter(RecruitmentInfoFilter filter, Set<Long> includeIds, Set<String> companyIds,
                                     Set<RecruitmentType> types, Set<ProgrammingLanguage> languages, Pageable pageable) {
        assert languages ? filter.languages == languages : true
        assert types ? filter.types == types : true
        assert companyIds ? filter.companyIds == companyIds : true
        assert includeIds ? filter.includeIds == includeIds : true
        assert filter.offset == pageable.getOffset()
        assert filter.pageSize == pageable.getPageSize()
    }

    def mockWikiRepository(List<RecruitmentInfoDetailsEntity> entities, RecruitmentInformationFilterRequest request) {
        entities.findAll {
            (request.includeIds ? request.includeIds.collect {it.value}.contains(it.id) : true) &&
            (request.companyIds ? request.companyIds.collect {it.value}.contains(it.companyId) : true) &&
            (request.types ? request.types.contains(it.recruitmentType) : true) &&
            (request.languages ? request.languages.contains(it.programmingLanguage) : true)
        } as List<RecruitmentInfoDetailsEntity>
    }

    def informationEntities(long size) {
        size > 0 ? (1..size).collect {
            [
                    id : it,
                    authorId : testUserId.value,
                    companyId: "c" + it,
                    content : "Solve for x: x+4=" + it,
                    upVotes : 7,
                    downVotes : 3,
                    authorReliabilityRatio : 0.7,
                    recruitmentType : INTERVIEW,
                    programmingLanguage : CPP
            ] as RecruitmentInfoDetailsEntity
        } : []
    }

}
