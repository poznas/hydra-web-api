package com.agh.hydra.register.entity;

import com.agh.hydra.common.model.AuthenticationProvider;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    /**
     * User identifier
     */
    private String id;

    /**
     * Username
     */
    private String username;

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
