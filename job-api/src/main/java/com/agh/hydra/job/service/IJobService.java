package com.agh.hydra.job.service;

import com.agh.hydra.common.model.UserId;
import com.agh.hydra.job.request.CreateJobAnnouncementRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface IJobService {

    /**
     * Creates job announcement entry
     * @param request create request
     * @param userId creator identifier
     */
    void createJobAnnouncement(@Valid @NotNull CreateJobAnnouncementRequest request, @Valid @NotNull UserId userId);
}
