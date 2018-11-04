package com.agh.hydra.register.dao;

import com.agh.hydra.register.entity.CompanyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyRepository {

    List<CompanyEntity> getCompaniesPageByIds(@Param("companyIds") List<String> companyIds,
                                              @Param("offset") long offset,
                                              @Param("pageSize") int pageSize);

    List<CompanyEntity> findCompanyByIds(@Param("companyIds") List<String> companyIds);

    void updateCompany(CompanyEntity entity);

    void invalidateCompanies(@Param("companyIds") List<String> companyIds);

    long getCompaniesCount(@Param("companyIds") List<String> companyIds);
}
