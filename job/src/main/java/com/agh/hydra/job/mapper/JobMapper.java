package com.agh.hydra.job.mapper;

import com.agh.hydra.common.mapper.BaseMapper;
import com.agh.hydra.job.entity.JobEntity;
import com.agh.hydra.job.model.JobAnnouncement;
import com.agh.hydra.job.model.JobAnnouncementFilter;
import com.agh.hydra.job.request.CreateJobAnnouncementRequest;
import com.agh.hydra.job.request.JobAnnouncementFilterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@SuppressWarnings("squid:S1214")
@Mapper(config = BaseMapper.class)
public interface JobMapper extends BaseMapper {

    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

    @Mappings({
            @Mapping(target = "jobId", ignore = true),
            @Mapping(target = "active", ignore = true),
            @Mapping(target = "programmingLanguages", ignore = true),
            @Mapping(target = "companyName", ignore = true)})
    JobEntity mapCreateRequest(CreateJobAnnouncementRequest request);

    @Mappings({
            @Mapping(target = "pageSize", ignore = true),
            @Mapping(target = "offset", ignore = true)})
    JobAnnouncementFilter mapFilter(JobAnnouncementFilterRequest request);

    @Mapping(target = "programmingLanguages", ignore = true)
    JobAnnouncement mapJob(JobEntity job);
}
