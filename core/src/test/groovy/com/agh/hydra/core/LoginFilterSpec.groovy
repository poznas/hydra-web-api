package com.agh.hydra.core

import com.agh.hydra.api.register.model.User
import com.agh.hydra.api.register.service.IPrivilegeService
import com.agh.hydra.api.register.service.IUserService
import com.agh.hydra.common.model.UserId
import com.agh.hydra.core.auth.filter.auth.LoginFilter
import com.agh.hydra.core.auth.service.TokenVerifier
import spock.lang.Specification

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static org.springframework.http.HttpStatus.UNAUTHORIZED

class LoginFilterSpec extends Specification {

    def tokenVerifier = Mock(TokenVerifier)
    def userService = Mock(IUserService)
    def privilegeService = Mock(IPrivilegeService)

    def servletRequest = Mock(HttpServletRequest)
    def servletResponse = Mock(HttpServletResponse)
    def filterChain = Mock(FilterChain)

    def filter = new LoginFilter(tokenVerifier, userService, privilegeService)

    def "retrieve and save user data, set RS headers, set RQ attribute"() {
        given:
        def userId = UserId.of("tester")
        def user = [id: userId] as User

        and:
        1 * servletRequest.getHeader("X-ID-TOKEN") >> "Google ID Token"
        1 * tokenVerifier.verifyTokenId("Google ID Token") >> user

        1 * userService.userExists(userId) >> false
        1 * userService.updateUser(user)
        1 * privilegeService.assignDefaultPrivileges(userId)

        1 * servletRequest.setAttribute("userId", userId)
        1 * servletResponse.addHeader("Authorization", _ as String)
        1 * servletResponse.addHeader("X-User-Id", userId.value)

        1 * filterChain.doFilter(servletRequest, servletResponse)

        when:
        filter.doFilter(servletRequest, servletResponse, filterChain)

        then:
        noExceptionThrown()
    }

    def "response with UNAUTHORIZED for invalid Google ID Token"() {
        given:
        1 * servletRequest.getHeader("X-ID-TOKEN") >> "Google ID Token"
        1 * tokenVerifier.verifyTokenId("Google ID Token") >> null

        0 * userService.userExists(_ as UserId) >> false
        0 * userService.updateUser(_ as User)
        0 * privilegeService.assignDefaultPrivileges(_ as UserId)

        0 * servletRequest.setAttribute("userId", _ as UserId)
        0 * servletResponse.addHeader("Authorization", _ as String)
        0 * servletResponse.addHeader("X-User-Id", _ as String)

        0 * filterChain.doFilter(servletRequest, servletResponse)

        1 * servletResponse.sendError(UNAUTHORIZED.value())

        when:
        filter.doFilter(servletRequest, servletResponse, filterChain)

        then:
        noExceptionThrown()
    }
}
