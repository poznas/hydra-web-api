package com.agh.hydra.wiki.mapper;

import com.agh.hydra.common.BaseMapper;
import com.agh.hydra.common.model.InformationId;
import com.agh.hydra.wiki.entity.RecruitmentInfoDetailsEntity;
import com.agh.hydra.wiki.entity.RecruitmentInfoEntity;
import com.agh.hydra.wiki.model.InformationContent;
import com.agh.hydra.wiki.model.InformationDetails;
import com.agh.hydra.wiki.model.RecruitmentInfoFilter;
import com.agh.hydra.wiki.request.CreateRecruitmentInfoRequest;
import com.agh.hydra.wiki.request.RecruitmentInformationFilterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import static com.agh.hydra.common.util.ValueObjectUtil.getValue;
import static com.agh.hydra.common.util.ValueObjectUtil.valueObject;

@Mapper(config = BaseMapper.class)
public interface WikiMapper extends BaseMapper {

    WikiMapper INSTANCE = Mappers.getMapper(WikiMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "authorId", ignore = true),
            @Mapping(target = "upVotes", ignore = true),
            @Mapping(target = "downVotes", ignore = true),
            @Mapping(target = "active", ignore = true)})
    RecruitmentInfoEntity mapCreateRequest(CreateRecruitmentInfoRequest request);

    @Mappings({
            @Mapping(target = "pageSize", ignore = true),
            @Mapping(target = "offset", ignore = true)})
    RecruitmentInfoFilter mapFilterRequest(RecruitmentInformationFilterRequest informationFilterRequest);

    InformationDetails mapInfoDetails(RecruitmentInfoDetailsEntity entity);

    default InformationContent mapInfoContent(String content) {
        return valueObject(content, InformationContent::of);
    }

    default String mapInfoContent(InformationContent content) {
        return getValue(content);
    }

    default InformationId mapInfoId(Long id) {
        return valueObject(id, InformationId::of);
    }

    default Long mapInfoId(InformationId id) {
        return getValue(id);
    }
}
