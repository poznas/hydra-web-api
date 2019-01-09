package com.agh.hydra.register.impl;

import com.agh.hydra.api.register.model.Company;
import com.agh.hydra.api.register.request.CompaniesRequest;
import com.agh.hydra.api.register.request.CreateCompanyRequest;
import com.agh.hydra.api.register.request.UpdateCompaniesRequest;
import com.agh.hydra.api.register.service.ICompanyService;
import com.agh.hydra.api.register.service.IPrivilegeService;
import com.agh.hydra.common.model.UserId;
import com.agh.hydra.common.util.ValueObjectUtil;
import com.agh.hydra.register.dao.CompanyRepository;
import com.agh.hydra.register.entity.CompanyEntity;
import com.agh.hydra.register.mapper.RegisterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.agh.hydra.common.exception.TechnicalException.COMPANY_NOT_FOUND;
import static com.agh.hydra.common.model.FunctionalPrivilege.FN_PRV_EDIT_COMPANIES;
import static com.agh.hydra.common.model.FunctionalPrivilege.FN_PRV_INVALIDATE_COMPANIES;
import static com.agh.hydra.common.util.CollectionUtils.mapList;
import static com.agh.hydra.common.util.CollectionUtils.mapSet;
import static com.google.common.collect.Iterables.transform;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class CompanyService implements ICompanyService {

    private final CompanyRepository companyRepository;
    private final IPrivilegeService privilegeService;

    @Override
    public void updateCompanies(@Valid @NotNull UpdateCompaniesRequest request, UserId userId) {
        privilegeService.throwIfUnprivileged(userId, FN_PRV_EDIT_COMPANIES);
        log.info("Attempt to update companies : {}", transform(request.getCompanies(), Company::getCompanyId));

        request.getCompanies().stream()
                .map(RegisterMapper.INSTANCE::mapCompany)
                .forEach(companyRepository::updateCompany);
    }

    @Override
    public void invalidateCompanies(@Valid @NotNull CompaniesRequest request, UserId userId) {
        privilegeService.throwIfUnprivileged(userId, FN_PRV_INVALIDATE_COMPANIES);
        log.info("Attempt to update companies : {}", request.getCompanyIds());

        var companyIds = mapList(request.getCompanyIds(), ValueObjectUtil::getValue);

        var companyEntities = companyRepository.findCompanyByIds(companyIds);

        companyIds.forEach(id -> {
                    if(!mapSet(companyEntities, CompanyEntity::getCompanyId).contains(id))
                        throw COMPANY_NOT_FOUND.throwWith(id);
                });
        companyRepository.invalidateCompanies(companyIds);
    }

    @Override
    public Page<Company> getCompanies(@Valid CompaniesRequest request, @NotNull Pageable pageable) {

        var companyIds = ofNullable(request)
                .map(CompaniesRequest::getCompanyIds)
                .map(ids -> mapList(ids, ValueObjectUtil::getValue))
                .orElse(null);

        var companyEntities = companyRepository
                .getCompaniesPageByIds(companyIds, pageable.getOffset(), pageable.getPageSize());

        var companies = mapList(companyEntities, RegisterMapper.INSTANCE::mapCompanyEntity);
        var total = companyRepository.getCompaniesCount(companyIds);

        return new PageImpl<>(companies, pageable, total);
    }

    @Override
    public void createCompany(CreateCompanyRequest request, UserId userId) {
        privilegeService.throwIfUnprivileged(userId, FN_PRV_EDIT_COMPANIES);
        companyRepository.createCompany(RegisterMapper.INSTANCE.mapCreateCompanyRequest(request));
    }
}
