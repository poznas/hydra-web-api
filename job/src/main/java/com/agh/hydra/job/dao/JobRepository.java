package com.agh.hydra.job.dao;

import com.agh.hydra.job.entity.JobEntity;
import com.agh.hydra.job.model.JobAnnouncementFilter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JobRepository {

    void createJobAnnouncement(JobEntity entity);

    List<JobEntity> getJobAnnouncements(JobAnnouncementFilter filter);

    long getJobAnnouncementCount(JobAnnouncementFilter filter);

    void invalidateJobAnnouncement(@Param("ids") List<Long> ids);
}
