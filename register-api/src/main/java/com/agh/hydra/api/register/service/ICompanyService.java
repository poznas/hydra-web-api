package com.agh.hydra.api.register.service;

import com.agh.hydra.api.register.model.Company;
import com.agh.hydra.api.register.request.CompaniesRequest;
import com.agh.hydra.api.register.request.CreateCompanyRequest;
import com.agh.hydra.api.register.request.UpdateCompaniesRequest;
import com.agh.hydra.common.model.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface ICompanyService {

    /**
     * Updates company entity in database
     * @param request update company request
     * @param userId user identifier
     */
    void updateCompanies(@Valid @NotNull UpdateCompaniesRequest request, UserId userId);

    /**
     * Changes active flag to false
     * @param request companies request
     * @param userId user identifier
     */
    void invalidateCompanies(@Valid @NotNull CompaniesRequest request, UserId userId);

    /**
     * Returns pageable list of companies
     * @param request optional companies request
     * @param pageable page parameters
     */
    Page<Company> getCompanies(@Valid CompaniesRequest request, @NotNull Pageable pageable);

    /**
     * Creates company entry with unique identifier
     * @param request company data
     * @param userId user identifier
     */
    void createCompany(CreateCompanyRequest request, UserId userId);
}
