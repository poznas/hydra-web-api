package com.agh.hydra.api.register.request;

import com.agh.hydra.common.model.FunctionalPrivilege;
import com.agh.hydra.common.model.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class UpdatePrivilegesRequest {

    /**
     * Target user identifier
     */
    @Valid
    @NotNull
    private UserId userId;

    /**
     * privileges list
     */
    @NotNull
    private Set<FunctionalPrivilege> privileges;
}
