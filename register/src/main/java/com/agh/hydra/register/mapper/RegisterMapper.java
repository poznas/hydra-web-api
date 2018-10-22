package com.agh.hydra.register.mapper;

import com.agh.hydra.api.register.model.Company;
import com.agh.hydra.common.model.CompanyId;
import com.agh.hydra.common.model.CompanyName;
import com.agh.hydra.common.util.ValueObjectUtil;
import com.agh.hydra.register.entity.CompanyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

import static com.agh.hydra.common.util.ValueObjectUtil.*;
import static java.util.Optional.ofNullable;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class RegisterMapper {

    public static RegisterMapper INSTANCE = Mappers.getMapper(RegisterMapper.class);

    public abstract CompanyEntity mapCompany(Company company);

    public String mapCompanyId(CompanyId id){
        return getValue(id);
    }

    public String mapCompanyName(CompanyName name){
        return getValue(name);
    }
}
