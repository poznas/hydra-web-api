package com.agh.hydra.register.impl;

import com.agh.hydra.api.register.model.User;
import com.agh.hydra.api.register.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class UserService implements IUserService {

    @Override
    public void updateUser(User user) {
        log.info(user.toString());
    }
}
