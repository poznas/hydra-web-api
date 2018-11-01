package com.agh.hydra.register;

import com.agh.hydra.common.model.UserId;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

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
}
