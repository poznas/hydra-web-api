package com.agh.hydra.register;

import com.agh.hydra.api.register.service.IPrivilegeService;
import com.agh.hydra.common.documentation.BaseDocumentation;
import com.agh.hydra.common.model.FunctionalPrivilege;
import com.agh.hydra.common.model.UserId;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.agh.hydra.common.util.ValueObjectUtil.getValue;
import static com.agh.hydra.register.AuthController.AUTH_CONTEXT;

@Slf4j
@Validated
@RestController
@RequestMapping(AUTH_CONTEXT)
@RequiredArgsConstructor
public class AuthController {

    static final String AUTH_CONTEXT = "/auth";
    private static final String AUTH_LOGIN = "/login";
    private static final String AUTH_PRIVILEGES = "/privileges/{userId}";

    private final IPrivilegeService privilegeService;

    @ApiOperation("Sign in with external credential provider")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK",
                    responseHeaders = {
                            @ResponseHeader(name = "Authorization",
                                    description = "OAuth 2.0 Bearer Token",
                                    response = String.class)}),
            @ApiResponse(code = 500, message = "Server error")
    })
    @ApiImplicitParams(
            @ApiImplicitParam(name = "X-ID-TOKEN", value = "Authentication provider success response ID token",
                    required = true, dataType = "string", paramType = "header"))
    @PostMapping(AUTH_LOGIN)
    public void login(@ApiIgnore @RequestAttribute UserId userId) {
        log.info("Login success : {}", getValue(userId));
    }

    @BaseDocumentation
    @GetMapping(AUTH_PRIVILEGES)
    public List<FunctionalPrivilege> getPrivileges(@Valid @NotNull @PathVariable("userId") UserId userId) {
        return privilegeService.getPrivileges(userId);
    }
}
