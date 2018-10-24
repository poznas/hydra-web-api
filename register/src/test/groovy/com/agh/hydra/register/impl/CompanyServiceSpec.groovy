package com.agh.hydra.register.impl

import com.agh.hydra.api.register.model.Company
import com.agh.hydra.api.register.request.UpdateCompaniesRequest
import com.agh.hydra.common.model.CompanyId
import com.agh.hydra.common.model.CompanyName
import com.agh.hydra.register.dao.CompanyRepository
import com.agh.hydra.register.entity.CompanyEntity
import spock.lang.Specification
import spock.lang.Unroll

import static com.agh.hydra.common.util.RandomUtils.randomString

class CompanyServiceSpec extends Specification {

    def companyRepository = Mock(CompanyRepository)

    def service = new CompanyService(companyRepository)

    @Unroll
    def "UpdateCompany() - happy path"() {
        given:
        def request = [companies : inputCompanies] as UpdateCompaniesRequest

        and:
        inputCompanies.size() * companyRepository.updateCompany(_ as CompanyEntity) >> {
            CompanyEntity entity ->
                assert inputCompanies.collect {it.companyId.value}.contains(entity.companyId)
        }
        when:
        service.updateCompanies(request)

        then:
        noExceptionThrown()

        where:
        inputCompanies | _
        companies(7)   | _
        companies(3)   | _
        companies(1)   | _
    }


    List<Company> companies(int size){
        return (1..size).collect {
            Company.builder()
                    .companyId(CompanyId.of(randomString(8)))
                    .companyName(CompanyName.of(randomString(10)))
                    .build()
        }
    }


}
