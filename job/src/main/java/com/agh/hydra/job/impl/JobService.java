package com.agh.hydra.job.impl;

import com.agh.hydra.api.register.service.IPrivilegeService;
import com.agh.hydra.common.model.UserId;
import com.agh.hydra.job.dao.JobRepository;
import com.agh.hydra.job.entity.JobEntity;
import com.agh.hydra.job.mapper.JobMapper;
import com.agh.hydra.job.request.CreateJobAnnouncementRequest;
import com.agh.hydra.job.service.IJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.agh.hydra.common.model.FunctionalPrivilege.FN_PRV_EDIT_JOBS;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class JobService implements IJobService {

    private final JobRepository jobRepository;
    private final IPrivilegeService privilegeService;

    @Override
    public void createJobAnnouncement(@Valid @NotNull CreateJobAnnouncementRequest request, @Valid @NotNull UserId userId) {
        privilegeService.throwIfUnprivileged(userId, FN_PRV_EDIT_JOBS);

        JobEntity entity = JobMapper.INSTANCE.mapCreateRequest(request);

        ofNullable(request.getProgrammingLanguages()).ifPresent(languages ->
                entity.setProgrammingLanguages(languages.stream().map(Enum::name).collect(joining(","))));

        jobRepository.createJobAnnouncement(entity);
    }
}
