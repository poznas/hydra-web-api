package com.agh.hydra.job.mapper;

import com.agh.hydra.common.BaseMapper;
import com.agh.hydra.common.model.JobId;
import com.agh.hydra.job.entity.JobEntity;
import com.agh.hydra.job.model.JobAnnouncement;
import com.agh.hydra.job.model.JobAnnouncementFilter;
import com.agh.hydra.job.model.JobTitle;
import com.agh.hydra.job.request.CreateJobAnnouncementRequest;
import com.agh.hydra.job.request.JobAnnouncementFilterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import static com.agh.hydra.common.util.ValueObjectUtil.getValue;
import static com.agh.hydra.common.util.ValueObjectUtil.valueObject;

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

    default JobId mapId(Long id) {
        return valueObject(id, JobId::of);
    }

    default Long mapId(JobId id) {
        return getValue(id);
    }

    default JobTitle mapTitle(String title) {
        return valueObject(title, JobTitle::of);
    }

    default String mapTitle(JobTitle title) {
        return getValue(title);
    }
}
