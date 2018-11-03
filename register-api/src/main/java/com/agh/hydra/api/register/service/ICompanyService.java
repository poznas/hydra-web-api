package com.agh.hydra.api.register.service;

import com.agh.hydra.api.register.request.CompaniesRequest;
import com.agh.hydra.api.register.request.UpdateCompaniesRequest;
import com.agh.hydra.common.model.UserId;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface ICompanyService {

    /**
     * Updates company entity in database
     * @param request update company request
     * @param userId
     */
    void updateCompanies(@Valid @NotNull UpdateCompaniesRequest request, UserId userId);

    /**
     * Changes active flag to false
     * @param request companies request
     * @param userId
     */
    void invalidateCompanies(@Valid @NotNull CompaniesRequest request, UserId userId);
}
