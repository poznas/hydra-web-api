package com.agh.hydra.register.impl;

import com.agh.hydra.api.register.service.IPrivilegeService;
import com.agh.hydra.common.model.FunctionalPrivilege;
import com.agh.hydra.common.model.UserId;
import com.agh.hydra.register.dao.PrivilegeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.agh.hydra.common.util.ValueObjectUtil.getValue;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class PrivilegeService implements IPrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    @Override
    public List<FunctionalPrivilege> getPrivileges(@Valid @NotNull UserId userId) {
        log.info("Retrieve privileges for user : {}", getValue(userId));

        return privilegeRepository.getPrivileges(getValue(userId));
    }
}
