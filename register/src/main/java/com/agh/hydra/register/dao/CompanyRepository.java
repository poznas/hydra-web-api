package com.agh.hydra.register.dao;

import com.agh.hydra.register.entity.CompanyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyRepository {

    List<CompanyEntity> findCompanyByIds(@Param("companyIds") List<String> companyIds);

    void updateCompany(CompanyEntity entity);

    void invalidateCompanies(@Param("companyIds") List<String> companyIds);
}
