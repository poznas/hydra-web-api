package com.agh.hydra.job.mapper;

import com.agh.hydra.common.BaseMapper;
import com.agh.hydra.job.entity.JobEntity;
import com.agh.hydra.job.request.CreateJobAnnouncementRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@SuppressWarnings("squid:S1214")
@Mapper(config = BaseMapper.class)
public interface JobMapper extends BaseMapper {

    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "active", ignore = true),
            @Mapping(target = "programmingLanguages", ignore = true)})
    JobEntity mapCreateRequest(CreateJobAnnouncementRequest request);
}
