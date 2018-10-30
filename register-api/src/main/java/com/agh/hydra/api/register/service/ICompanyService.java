package com.agh.hydra.api.register.service;

import com.agh.hydra.api.register.request.CompaniesRequest;
import com.agh.hydra.api.register.request.UpdateCompaniesRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface ICompanyService {

    /**
     * Updates company entity in database
     * @param request update company request
     */
    void updateCompanies(@Valid @NotNull UpdateCompaniesRequest request);

    /**
     * Changes active flag to false
     * @param request companies request
     */
    void invalidateCompanies(@Valid @NotNull CompaniesRequest request);
}
