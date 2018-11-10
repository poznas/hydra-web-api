package com.agh.hydra.register.mapper;

import com.agh.hydra.api.register.model.Company;
import com.agh.hydra.api.register.model.User;
import com.agh.hydra.common.BaseMapper;
import com.agh.hydra.common.model.Language;
import com.agh.hydra.register.entity.CompanyEntity;
import com.agh.hydra.register.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(config = BaseMapper.class)
public interface RegisterMapper extends BaseMapper {

    RegisterMapper INSTANCE = Mappers.getMapper(RegisterMapper.class);

    @Mappings({@Mapping(target = "active", ignore = true),
            @Mapping(target = "language", source = "language.lowerCase")})
    CompanyEntity mapCompany(Company company);

    Company mapCompanyEntity(CompanyEntity entity);

    UserEntity mapUser(User user);

    default Language mapLanguage(String lang) {
        return Language.from(lang);
    }
}
