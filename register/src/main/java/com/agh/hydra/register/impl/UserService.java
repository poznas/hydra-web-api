package com.agh.hydra.register.impl;

import com.agh.hydra.api.register.model.User;
import com.agh.hydra.api.register.service.IUserService;
import com.agh.hydra.common.model.UserId;
import com.agh.hydra.register.dao.UserRepository;
import com.agh.hydra.register.mapper.RegisterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.agh.hydra.common.util.ValueObjectUtil.getValue;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Override
    public void updateUser(@Valid @NotNull User user) {
        log.info("Update user data : {}", user);
        userRepository.updateUser(RegisterMapper.INSTANCE.mapUser(user));
    }

    @Override
    public boolean userExists(@Valid @NotNull UserId userId) {
        return userRepository.userExists(getValue(userId));
    }
}
