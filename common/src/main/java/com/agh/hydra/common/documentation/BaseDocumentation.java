package com.agh.hydra.common.documentation;

import io.swagger.annotations.*;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ApiImplicitParams(
        @ApiImplicitParam(name = "Authorization",
                value = "OAuth 2.0 Bearer Token",
                required = true,
                dataType = "string",
                paramType = "header")
)
@ApiResponses(value = {
        @ApiResponse(
                code = 200,
                message = "OK",
                responseHeaders = {
                        @ResponseHeader(
                                name = "Authorization",
                                description = "OAuth 2.0 Bearer Token",
                                response = String.class)}),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 500, message = "Server error")
}
)
public @interface BaseDocumentation {
}
