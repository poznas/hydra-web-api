package com.agh.hydra.api.register.model;

import com.agh.hydra.common.model.AuthenticationProvider;
import com.agh.hydra.common.model.UserId;
import com.agh.hydra.common.model.Username;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * User identifier
     */
    @Valid
    @NotNull
    private UserId id;

    /**
     * Username
     */
    @Valid
    @NotNull
    private Username username;

    /**
     * e-mail address
     */
    private String email;

    /**
     * image URL address
     */
    private String imageUrl;

    /**
     * external authentication provider
     */
    private AuthenticationProvider provider;
}
