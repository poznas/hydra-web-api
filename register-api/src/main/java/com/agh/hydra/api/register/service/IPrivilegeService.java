package com.agh.hydra.api.register.service;

import com.agh.hydra.common.model.FunctionalPrivilege;
import com.agh.hydra.common.model.UserId;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface IPrivilegeService {

    /**
     * @param userId user identifier
     * @return all privileges assigned to user
     */
    List<FunctionalPrivilege> getPrivileges(@Valid @NotNull UserId userId);
}
