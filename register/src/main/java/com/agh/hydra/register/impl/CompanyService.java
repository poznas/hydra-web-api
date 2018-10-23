package com.agh.hydra.register.impl;

import com.agh.hydra.api.register.model.Company;
import com.agh.hydra.api.register.request.UpdateCompaniesRequest;
import com.agh.hydra.api.register.service.ICompanyService;
import com.agh.hydra.register.dao.CompanyRepository;
import com.agh.hydra.register.mapper.RegisterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.google.common.collect.Iterables.transform;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class CompanyService implements ICompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public ResponseEntity updateCompany(@Valid @NotNull UpdateCompaniesRequest request) {
        log.info("Attempt to update companies : {}", transform(request.getCompanies(), Company::getCompanyId));

        request.getCompanies().stream()
                .map(RegisterMapper.INSTANCE::mapCompany)
                .forEach(companyRepository::updateCompany);

        return new ResponseEntity(OK);
    }
}
