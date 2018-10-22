package com.agh.hydra.register.dao;

import com.agh.hydra.common.model.CompanyId;
import com.agh.hydra.register.entity.CompanyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper()
public interface CompanyRepository {

    CompanyEntity findCompanyById(@Param("companyId") CompanyId companyId);

    void updateCompany(CompanyEntity entity);
}
