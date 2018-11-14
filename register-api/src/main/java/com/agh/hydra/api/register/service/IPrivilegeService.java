package com.agh.hydra.api.register.service;

import com.agh.hydra.api.register.request.UpdatePrivilegesRequest;
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

    /**
     * Checks whether user has given privilege
     * @param userId user identifier
     * @param privilege privilege
     */
    boolean hasPrivilege(@Valid @NotNull UserId userId, @NotNull FunctionalPrivilege privilege);

    /**
     * Throws exception if user has not given privilege
     * @param userId user identifier
     * @param privilege privilege
     */
    void throwIfUnprivileged(@Valid @NotNull UserId userId, @NotNull FunctionalPrivilege privilege);

    /**
     * Assigns new privileges to user specified in request
     * @param request update privileges request
     * @param performerId performer identifier
     */
    void addPrivileges(@Valid @NotNull UpdatePrivilegesRequest request, @Valid @NotNull UserId performerId);

    /**
     * Removes privileges assigned to user specified in request
     * @param request update privileges request
     * @param performerId performer identifier
     */
    void removePrivileges(@Valid @NotNull UpdatePrivilegesRequest request, @Valid @NotNull UserId performerId);

    /**
     * Assigns default set of functional privileges to user
     * @param id user identifier
     */
    void assignDefaultPrivileges(@Valid @NotNull UserId id);
}
