package com.agh.hydra.register.impl

import com.agh.hydra.api.register.model.Company
import com.agh.hydra.api.register.request.CompaniesRequest
import com.agh.hydra.api.register.request.UpdateCompaniesRequest
import com.agh.hydra.api.register.service.IPrivilegeService
import com.agh.hydra.common.model.CompanyId
import com.agh.hydra.common.model.CompanyName
import com.agh.hydra.common.model.FunctionalPrivilege
import com.agh.hydra.common.model.UserId
import com.agh.hydra.register.dao.CompanyRepository
import com.agh.hydra.register.entity.CompanyEntity
import org.springframework.data.domain.PageRequest
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static com.agh.hydra.common.model.FunctionalPrivilege.FN_PRV_EDIT_COMPANIES
import static com.agh.hydra.common.model.FunctionalPrivilege.FN_PRV_INVALIDATE_COMPANIES
import static com.agh.hydra.common.util.CollectionUtils.haveSameElements
import static com.agh.hydra.common.util.RandomUtils.randomString
import static com.agh.hydra.common.util.ValueObjectUtil.getValue
import static java.util.Arrays.asList
import static java.util.Collections.emptyList
import static java.util.Collections.singletonList

class CompanyServiceSpec extends Specification {

    def companyRepository = Mock(CompanyRepository)
    def privilegeService = Mock(IPrivilegeService)

    def service = new CompanyService(companyRepository, privilegeService)

    @Shared def testUserId = UserId.of("1")

    @Unroll
    def "updateCompanies() - happy path"() {
        given:

        def request = [companies : inputCompanies] as UpdateCompaniesRequest

        and:
        1 * privilegeService.throwIfUnprivileged(testUserId, FN_PRV_EDIT_COMPANIES)
        inputCompanies.size() * companyRepository.updateCompany(_ as CompanyEntity) >> {
            CompanyEntity entity ->
                assert inputCompanies.collect {it.companyId.value}.contains(entity.companyId)
        }

        when:
        service.updateCompanies(request, testUserId)

        then:
        noExceptionThrown()

        where:
        inputCompanies | _
        companies(7)   | _
        companies(3)   | _
        companies(1)   | _
    }

    @Unroll
    def "invalidateCompanies() - happy path"() {
        given:
        def request = CompaniesRequest.of(requestedIds.collect {CompanyId.of(it)} as Set)

        and:
        1 * privilegeService.throwIfUnprivileged(testUserId, FN_PRV_INVALIDATE_COMPANIES)
        1 * companyRepository.findCompanyByIds(requestedIds) >> companyEntities(requestedIds.size())
        1 * companyRepository.invalidateCompanies(requestedIds)

        when:
        service.invalidateCompanies(request, testUserId)

        then:
        noExceptionThrown()

        where:
        requestedIds             | _
        asList("c1", "c2")       | _
        asList("c1", "c2", "c3") | _
        singletonList("c1")      | _
    }

    @Unroll
    def "invalidateCompanies() - should throw an exception"() {
        given:
        def request = CompaniesRequest.of(requestedIds.collect {CompanyId.of(it)} as Set)

        and:
        1 * privilegeService.throwIfUnprivileged(testUserId, FN_PRV_INVALIDATE_COMPANIES)
        1 * companyRepository.findCompanyByIds(requestedIds) >> companyEntities(requestedIds.size())
        0 * companyRepository.invalidateCompanies(_ as List<String>)

        when:
        service.invalidateCompanies(request, testUserId)

        then:
        def exception = thrown(RuntimeException.class)
        assert exception.message == expectedMessage

        where:
        requestedIds             || expectedMessage
        asList("c1", "c4")       || "Company 'c4' does not exist"
        asList("c1", "c2", "h2") || "Company 'h2' does not exist"
        singletonList("c6")      || "Company 'c6' does not exist"
    }

    @Unroll
    def "getCompanies() - should return paged content"() {
        given:
        def request = requestedIds ? CompaniesRequest.of(requestedIds.collect {CompanyId.of(it)} as Set) : null
        def pageable = PageRequest.of(0, 10)

        and:
        def repoContent = companyEntities(repoSize)
        def repoResult = mockCompanyRepositoryLogic(repoContent, requestedIds)

        and:
        0 * privilegeService.throwIfUnprivileged(testUserId, _ as FunctionalPrivilege)
        1 * companyRepository.getCompaniesPageByIds(requestedIds, pageable.getOffset(), pageable.getPageSize()) >> repoResult
        1 * companyRepository.getCompaniesCount(requestedIds) >> repoResult.size()

        when:
        def result = service.getCompanies(request, pageable)

        then:
        assert result.number == 0
        assert result.size == 10
        assert result.totalPages == (repoSize > 0 ? 1 : 0)
        assert result.numberOfElements == expectedIds.size()
        assert result.totalElements == expectedIds.size()
        assert haveSameElements(result.content.collect {getValue(it.companyId)}, expectedIds)

        where:
        requestedIds             | repoSize || expectedIds
        singletonList("c1")      | 0        || emptyList()
        asList("c1", "c2")       | 6        || asList("c1", "c2")
        asList("c1", "c2", "c3") | 2        || asList("c1", "c2")
        null                     | 4        || asList("c1", "c2", "c3", "c4")

    }

    def mockCompanyRepositoryLogic(List<CompanyEntity> repoContent, List<String> ids) {
        (ids ? repoContent.findAll { ids.contains(it.companyId) } : repoContent) as List<CompanyEntity>
    }

    def companies(int size){
        (1..size).collect {
            Company.builder()
                    .companyId(CompanyId.of("c" + it))
                    .companyName(CompanyName.of(randomString(10)))
                    .build()
        }
    }

    def companyEntities(int size){
        size > 0 ? (1..size).collect {
            CompanyEntity.builder()
                    .companyId("c" + it)
                    .companyName(randomString(10))
                    .build()
        } : []
    }


}
