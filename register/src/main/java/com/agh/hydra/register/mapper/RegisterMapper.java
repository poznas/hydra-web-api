package com.agh.hydra.register.mapper;

import com.agh.hydra.api.register.model.Company;
import com.agh.hydra.api.register.model.User;
import com.agh.hydra.common.model.CompanyId;
import com.agh.hydra.common.model.CompanyName;
import com.agh.hydra.common.model.Language;
import com.agh.hydra.register.entity.CompanyEntity;
import com.agh.hydra.register.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import static com.agh.hydra.common.util.ValueObjectUtil.valueObject;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class RegisterMapper {

    public static RegisterMapper INSTANCE = Mappers.getMapper(RegisterMapper.class);

    @Mappings({@Mapping(target = "active", ignore = true),
            @Mapping(target = "companyId", source = "companyId.value"),
            @Mapping(target = "companyName", source = "companyName.value"),
            @Mapping(target = "language", source = "language.lowerCase")})
    public abstract CompanyEntity mapCompany(Company company);

    public abstract Company mapCompanyEntity(CompanyEntity entity);

    CompanyId mapCompanyId(String id) {
        return valueObject(id, CompanyId::of);
    }

    CompanyName mapCompanyName(String name) {
        return valueObject(name, CompanyName::of);
    }

    Language mapLanguage(String lang) {
        return Language.from(lang);
    }

    @Mappings({@Mapping(target = "id", source = "id.value"),
            @Mapping(target = "username", source = "username.value")})
    public abstract UserEntity mapUser(User user);
}
