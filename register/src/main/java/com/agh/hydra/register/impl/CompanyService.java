package com.agh.hydra.register.impl;

import com.agh.hydra.api.register.model.Company;
import com.agh.hydra.api.register.request.CompaniesRequest;
import com.agh.hydra.api.register.request.UpdateCompaniesRequest;
import com.agh.hydra.api.register.service.ICompanyService;
import com.agh.hydra.common.util.ValueObjectUtil;
import com.agh.hydra.register.dao.CompanyRepository;
import com.agh.hydra.register.entity.CompanyEntity;
import com.agh.hydra.register.mapper.RegisterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.agh.hydra.common.util.CollectionUtils.mapList;
import static com.agh.hydra.common.util.CollectionUtils.mapSet;
import static com.google.common.collect.Iterables.transform;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class CompanyService implements ICompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public void updateCompanies(@Valid @NotNull UpdateCompaniesRequest request) {
        log.info("Attempt to update companies : {}", transform(request.getCompanies(), Company::getCompanyId));

        request.getCompanies().stream()
                .map(RegisterMapper.INSTANCE::mapCompany)
                .forEach(companyRepository::updateCompany);
    }

    @Override
    public void invalidateCompanies(@Valid @NotNull CompaniesRequest request) {
        log.info("Attempt to update companies : {}", request.getCompanyIds());

        List<String> companyIds = mapList(request.getCompanyIds(), ValueObjectUtil::getValue);

        List<CompanyEntity> companyEntities = companyRepository.findCompanyByIds(companyIds);

        companyIds.forEach(id -> {
                    if(!mapSet(companyEntities, CompanyEntity::getCompanyId).contains(id))
                        throw new RuntimeException("Company '" + id + "' does not exist");
                });
        companyRepository.invalidateCompanies(companyIds);
    }
}
