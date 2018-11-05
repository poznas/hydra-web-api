package com.agh.hydra.wiki.mapper;

import com.agh.hydra.wiki.entity.RecruitmentInfoEntity;
import com.agh.hydra.wiki.request.CreateRecruitmentInfoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class WikiMapper {

    public static WikiMapper INSTANCE = Mappers.getMapper(WikiMapper.class);

    @Mappings({
            @Mapping(target = "companyId", source = "companyId.value"),
            @Mapping(target = "content", source = "content.value"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "authorId", ignore = true),
            @Mapping(target = "upVotes", ignore = true),
            @Mapping(target = "downVotes", ignore = true),
            @Mapping(target = "active", ignore = true)})
    public abstract RecruitmentInfoEntity mapCreateRequest(CreateRecruitmentInfoRequest request);
}
