package com.agh.hydra.register.impl;

import com.agh.hydra.api.register.model.Company;
import com.agh.hydra.api.register.request.UpdateCompaniesRequest;
import com.agh.hydra.api.register.service.ICompanyService;
import com.agh.hydra.common.model.CompanyId;
import com.agh.hydra.register.dao.CompanyRepository;
import com.agh.hydra.register.mapper.RegisterMapper;
import com.google.common.collect.Iterables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.google.common.collect.Iterables.transform;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class CompanyService implements ICompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public ResponseEntity updateCompany(@Valid @NotNull UpdateCompaniesRequest request) {
        log.info("Attempt to update companies : {}", transform(request.getCompanies(), Company::getCompanyId));

        log.info("mapped : {}", RegisterMapper.INSTANCE.mapCompany(request.getCompanies().get(0)));

        log.info("get : {}", companyRepository.findCompanyById(CompanyId.of("ailleron")));
        return null;
    }
}
