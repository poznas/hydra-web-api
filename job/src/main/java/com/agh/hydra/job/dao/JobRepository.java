package com.agh.hydra.job.dao;

import com.agh.hydra.job.entity.JobEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JobRepository {

    void createJobAnnouncement(JobEntity entity);
}
