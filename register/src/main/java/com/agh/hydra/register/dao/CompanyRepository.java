package com.agh.hydra.register.dao;

import com.agh.hydra.register.entity.CompanyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CompanyRepository {

    CompanyEntity findCompanyById(@Param("companyId") String companyId);

    void updateCompany(CompanyEntity entity);
}
