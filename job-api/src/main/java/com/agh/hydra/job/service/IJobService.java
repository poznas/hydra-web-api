package com.agh.hydra.job.service;

import com.agh.hydra.common.model.UserId;
import com.agh.hydra.job.model.JobAnnouncement;
import com.agh.hydra.job.request.CreateJobAnnouncementRequest;
import com.agh.hydra.job.request.JobAnnouncementFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface IJobService {

    /**
     * Creates job announcement entry
     *
     * @param request create request
     * @param userId  creator identifier
     */
    void createJobAnnouncement(@Valid @NotNull CreateJobAnnouncementRequest request, @Valid @NotNull UserId userId);

    /**
     * @param request  filter request
     * @param pageable pageable
     * @return filtered, paged and detailed job announcement list
     */
    Page<JobAnnouncement> getJobAnnouncements(@Valid JobAnnouncementFilterRequest request, @NotNull Pageable pageable);
}
