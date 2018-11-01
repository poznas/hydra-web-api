package com.agh.hydra.api.register.service;

import com.agh.hydra.api.register.model.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface IUserService {

    /**
     * Updates user entity in database
     * @param user user data
     */
    void updateUser(@Valid @NotNull User user);
}
