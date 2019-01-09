package com.agh.hydra.register.impl

import com.agh.hydra.api.register.request.UpdatePrivilegesRequest
import com.agh.hydra.common.model.UserId
import com.agh.hydra.register.dao.PrivilegeRepository
import org.springframework.web.server.ResponseStatusException
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static com.agh.hydra.common.model.FunctionalPrivilege.*
import static com.agh.hydra.common.util.CollectionUtils.asSet
import static java.util.Arrays.asList

class PrivilegeServiceSpec extends Specification {

    def privilegeRepository = Mock(PrivilegeRepository)

    def service = new PrivilegeService(privilegeRepository)

    @Shared def testUserId = UserId.of("1")
    @Shared def testPrivileges = asList(FN_PRV_EDIT_COMPANIES)

    def "getPrivileges() - happy path"() {
        given:
        1 * privilegeRepository.getPrivileges(testUserId.value) >> testPrivileges

        when:
        def result = service.getPrivileges(testUserId)

        then:
        assert result == testPrivileges
    }

    @Unroll
    def "hasPrivilege() - happy path"() {
        given:
        1 * privilegeRepository.hasPrivilege(testUserId.value, privilege.name()) >> repoResult

        when:
        def result = service.hasPrivilege(testUserId, privilege)

        then:
        assert result == expectedResult

        where:
        privilege                   | repoResult || expectedResult
        FN_PRV_EDIT_COMPANIES       | true       || true
        FN_PRV_CREATE_INFORMATION   | true       || true
        FN_PRV_INVALIDATE_COMPANIES | false      || false
    }

    @Unroll
    def "throwIfUnprivileged() - happy path"() {
        given:
        1 * privilegeRepository.hasPrivilege(testUserId.value, privilege.name()) >> repoResult

        when:
        service.throwIfUnprivileged(testUserId, privilege)

        then:
        noExceptionThrown()

        where:
        privilege                   | repoResult
        FN_PRV_EDIT_COMPANIES       | true
        FN_PRV_CREATE_INFORMATION   | true
    }

    @Unroll
    def "throwIfUnprivileged() - throw UNPRIVILEGED"() {
        given:
        1 * privilegeRepository.hasPrivilege(testUserId.value, privilege.name()) >> repoResult

        when:
        service.throwIfUnprivileged(testUserId, privilege)

        then:
        def exception = thrown(expectedException)
        assert exception.message == "Response status 400 with reason " +
                "\"User is not privileged to perform this action\""

        where:
        privilege                 | repoResult || expectedException
        FN_PRV_EDIT_COMPANIES     | false      || ResponseStatusException.class
        FN_PRV_CREATE_INFORMATION | false      || ResponseStatusException.class
    }

    @Unroll
    def "addPrivileges() - happy path"() {
        given:
        def request = [
                userId : testUserId,
                privileges : privileges
        ] as UpdatePrivilegesRequest

        1 * privilegeRepository.hasPrivilege(testUserId.value, FN_PRV_EDIT_PRIVILEGES.name()) >> true
        1 * privilegeRepository.addPrivileges(testUserId.value, request.getPrivileges())

        when:
        service.addPrivileges(request, testUserId)

        then:
        noExceptionThrown()

        where:
        privileges                  | _
        [FN_PRV_EDIT_COMPANIES]     | _
        [FN_PRV_CREATE_INFORMATION] | _
    }

    @Unroll
    def "removePrivileges() - happy path"() {
        given:
        def request = [
                userId : testUserId,
                privileges : privileges
        ] as UpdatePrivilegesRequest

        1 * privilegeRepository.hasPrivilege(testUserId.value, FN_PRV_EDIT_PRIVILEGES.name()) >> true
        1 * privilegeRepository.removePrivileges(testUserId.value, request.getPrivileges())

        when:
        service.removePrivileges(request, testUserId)

        then:
        noExceptionThrown()

        where:
        privileges                  | _
        [FN_PRV_EDIT_COMPANIES]     | _
        [FN_PRV_CREATE_INFORMATION] | _
    }

    def "assignDefaultPrivileges() - happy path"() {
        given:
        def expectedDefaultPrivileges = asSet(
                FN_PRV_CREATE_INFORMATION, FN_PRV_EDIT_JOBS, FN_PRV_CREATE_REFERRAL)

        1 * privilegeRepository.addPrivileges(testUserId.value, expectedDefaultPrivileges)

        when:
        service.assignDefaultPrivileges(testUserId)

        then:
        noExceptionThrown()
    }

}
