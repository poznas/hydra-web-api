package com.agh.hydra.job.impl;

import com.agh.hydra.api.register.service.IPrivilegeService;
import com.agh.hydra.common.model.ProgrammingLanguage;
import com.agh.hydra.common.model.UserId;
import com.agh.hydra.common.model.ValueObject;
import com.agh.hydra.common.util.PageableUtils;
import com.agh.hydra.job.dao.JobRepository;
import com.agh.hydra.job.entity.JobEntity;
import com.agh.hydra.job.mapper.JobMapper;
import com.agh.hydra.job.model.JobAnnouncement;
import com.agh.hydra.job.model.JobAnnouncementFilter;
import com.agh.hydra.job.request.CreateJobAnnouncementRequest;
import com.agh.hydra.job.request.JobAnnouncementFilterRequest;
import com.agh.hydra.job.request.JobAnnouncementRequest;
import com.agh.hydra.job.service.IJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.agh.hydra.common.model.FunctionalPrivilege.FN_PRV_EDIT_JOBS;
import static com.agh.hydra.common.model.FunctionalPrivilege.FN_PRV_INVALIDATE_JOBS;
import static com.agh.hydra.common.util.CollectionUtils.mapList;
import static com.agh.hydra.common.util.ObjectUtils.getOrDefault;
import static com.agh.hydra.common.util.ObjectUtils.getOrNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

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

    @Override
    public Page<JobAnnouncement> getJobAnnouncements(@Valid JobAnnouncementFilterRequest request, @NotNull Pageable pageable) {

        JobAnnouncementFilter filter = getOrDefault(request, JobMapper.INSTANCE::mapFilter, new JobAnnouncementFilter());

        PageableUtils.setPageableParams(filter, pageable);

        List<JobAnnouncement> announcements = mapList(jobRepository.getJobAnnouncements(filter),
                job -> {
                    JobAnnouncement announcement = JobMapper.INSTANCE.mapJob(job);
                    announcement.setProgrammingLanguages(parseLanguages(job.getProgrammingLanguages()));
                    return announcement;
                });
        long total = jobRepository.getJobAnnouncementCount(filter);

        return new PageImpl<>(announcements, pageable, total);
    }

    @Override
    public void invalidateJobAnnouncement(@Valid @NotNull JobAnnouncementRequest request, @Valid @NotNull UserId userId) {
        privilegeService.throwIfUnprivileged(userId, FN_PRV_INVALIDATE_JOBS);
        jobRepository.invalidateJobAnnouncement(mapList(request.getIds(), ValueObject::getValue));
    }

    private Set<ProgrammingLanguage> parseLanguages(String languages) {
        return getOrNull(languages, lang -> Stream.of(lang.split(",")).map(ProgrammingLanguage::valueOf).collect(toSet()));
    }


}
