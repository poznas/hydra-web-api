package com.agh.hydra.job;

import com.agh.hydra.common.documentation.BaseDocumentation;
import com.agh.hydra.common.documentation.BasePageDocumentation;
import com.agh.hydra.common.model.UserId;
import com.agh.hydra.job.model.JobAnnouncement;
import com.agh.hydra.job.request.CreateJobAnnouncementRequest;
import com.agh.hydra.job.request.JobAnnouncementFilterRequest;
import com.agh.hydra.job.service.IJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.agh.hydra.job.JobController.JOB_CONTEXT;

@Slf4j
@Validated
@RestController
@RequestMapping(JOB_CONTEXT)
@RequiredArgsConstructor
public class JobController {

    static final String JOB_CONTEXT = "/job";
    private static final String JOB_CREATE_ANNOUNCEMENT = "/add";
    private static final String JOB_ANNOUNCEMENTS = "/jobs";

    private final IJobService jobService;

    @BaseDocumentation
    @PostMapping(JOB_CREATE_ANNOUNCEMENT)
    public void createJobAnnouncement(@Valid @NotNull @RequestBody CreateJobAnnouncementRequest request,
                                      @ApiIgnore @RequestAttribute UserId userId) {
        jobService.createJobAnnouncement(request, userId);
    }

    @BasePageDocumentation
    @GetMapping(JOB_ANNOUNCEMENTS)
    public Page<JobAnnouncement> getJobAnnouncements(@Valid @Nullable @RequestBody JobAnnouncementFilterRequest request,
                                                     @ApiIgnore @PageableDefault Pageable pageable) {
        return jobService.getJobAnnouncements(request, pageable);
    }
}
