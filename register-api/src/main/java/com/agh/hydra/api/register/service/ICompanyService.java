package com.agh.hydra.api.register.service;

import com.agh.hydra.api.register.request.UpdateCompaniesRequest;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface ICompanyService {

    /**
     * Updates company entity in database
     * @param request update company request
     */
    ResponseEntity updateCompany(@Valid @NotNull UpdateCompaniesRequest request);
}
