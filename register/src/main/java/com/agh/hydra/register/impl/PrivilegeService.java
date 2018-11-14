package com.agh.hydra.register.impl;

import com.agh.hydra.api.register.request.UpdatePrivilegesRequest;
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
import java.util.Set;

import static com.agh.hydra.common.exception.BusinessException.UNPRIVILEGED;
import static com.agh.hydra.common.model.FunctionalPrivilege.FN_PRV_CREATE_INFORMATION;
import static com.agh.hydra.common.model.FunctionalPrivilege.FN_PRV_EDIT_PRIVILEGES;
import static com.agh.hydra.common.util.CollectionUtils.asSet;
import static com.agh.hydra.common.util.ValueObjectUtil.getValue;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class PrivilegeService implements IPrivilegeService {

    private static final Set<FunctionalPrivilege> DEFAULT_FN_PRIVILEGES = asSet(FN_PRV_CREATE_INFORMATION);

    private final PrivilegeRepository privilegeRepository;

    @Override
    public List<FunctionalPrivilege> getPrivileges(@Valid @NotNull UserId userId) {
        log.info("Retrieve privileges for user : {}", getValue(userId));

        return privilegeRepository.getPrivileges(getValue(userId));
    }

    @Override
    public boolean hasPrivilege(@Valid @NotNull UserId userId, @NotNull FunctionalPrivilege privilege) {
        return privilegeRepository.hasPrivilege(getValue(userId), privilege.name());
    }

    @Override
    public void throwIfUnprivileged(@Valid @NotNull UserId userId, @NotNull FunctionalPrivilege privilege) {
        if(!hasPrivilege(userId, privilege)){
            throw UNPRIVILEGED.getException();
        }
    }

    @Override
    public void addPrivileges(@Valid @NotNull UpdatePrivilegesRequest request, @Valid @NotNull UserId performerId) {
        throwIfUnprivileged(performerId, FN_PRV_EDIT_PRIVILEGES);

        privilegeRepository.addPrivileges(getValue(request.getUserId()), request.getPrivileges());
    }

    @Override
    public void removePrivileges(@Valid @NotNull UpdatePrivilegesRequest request, @Valid @NotNull UserId performerId) {
        throwIfUnprivileged(performerId, FN_PRV_EDIT_PRIVILEGES);

        privilegeRepository.removePrivileges(getValue(request.getUserId()), request.getPrivileges());
    }

    @Override
    public void assignDefaultPrivileges(@Valid @NotNull UserId id) {
        privilegeRepository.addPrivileges(getValue(id), DEFAULT_FN_PRIVILEGES);
    }
}
