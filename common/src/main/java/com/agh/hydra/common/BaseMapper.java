package com.agh.hydra.common;

import com.agh.hydra.common.model.*;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

import static com.agh.hydra.common.util.ValueObjectUtil.getValue;
import static com.agh.hydra.common.util.ValueObjectUtil.valueObject;

@MapperConfig(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface BaseMapper {

    default CompanyId mapCompanyId(String id) {
        return valueObject(id, CompanyId::of);
    }

    default String mapCompanyId(CompanyId id) {
        return getValue(id);
    }

    default CompanyName mapCompanyName(String name) {
        return valueObject(name, CompanyName::of);
    }

    default String mapCompanyId(CompanyName name) {
        return getValue(name);
    }

    default Username mapUserName(String name) {
        return valueObject(name, Username::of);
    }

    default String mapUserName(Username name) {
        return getValue(name);
    }

    default UserId mapUserId(String id) {
        return valueObject(id, UserId::of);
    }

    default String mapCompanyId(UserId id) {
        return getValue(id);
    }

    default InformationContent mapInfoContent(String content) {
        return valueObject(content, InformationContent::of);
    }

    default String mapInfoContent(InformationContent content) {
        return getValue(content);
    }
}
