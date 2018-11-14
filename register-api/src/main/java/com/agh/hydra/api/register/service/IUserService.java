package com.agh.hydra.api.register.service;

import com.agh.hydra.api.register.model.User;
import com.agh.hydra.common.model.UserId;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface IUserService {

    /**
     * Updates user entity in database
     * @param user user data
     */
    void updateUser(@Valid @NotNull User user);

    /**
     * @param userId user identifier
     * @return true if user exists in db
     */
    boolean userExists(@Valid @NotNull UserId userId);
}
