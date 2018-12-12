package com.agh.hydra.referral.impl;

import com.agh.hydra.api.register.service.IPrivilegeService;
import com.agh.hydra.common.model.JobId;
import com.agh.hydra.common.model.UserId;
import com.agh.hydra.common.model.ValueObject;
import com.agh.hydra.common.util.PageableUtils;
import com.agh.hydra.job.model.JobAnnouncement;
import com.agh.hydra.job.request.JobAnnouncementFilterRequest;
import com.agh.hydra.job.service.IJobService;
import com.agh.hydra.referral.dao.ReferralRepository;
import com.agh.hydra.referral.entity.ReferralEntity;
import com.agh.hydra.referral.mapper.ReferralMapper;
import com.agh.hydra.referral.model.ReferralAnnouncement;
import com.agh.hydra.referral.model.ReferralAnnouncementFilter;
import com.agh.hydra.referral.request.CreateReferralRequest;
import com.agh.hydra.referral.request.ReferralAnnouncementFilterRequest;
import com.agh.hydra.referral.request.ReferralAnnouncementRequest;
import com.agh.hydra.referral.service.IReferralService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.agh.hydra.common.exception.BusinessException.ACTIVE_REFERRAL_EXISTS;
import static com.agh.hydra.common.exception.BusinessException.INVALID_REFERRAL_CLOSING_DATE;
import static com.agh.hydra.common.exception.TechnicalException.JOB_ANNOUNCEMENT_NOT_FOUND;
import static com.agh.hydra.common.model.FunctionalPrivilege.FN_PRV_CREATE_REFERRAL;
import static com.agh.hydra.common.model.FunctionalPrivilege.FN_PRV_INVALIDATE_REFERRAL;
import static com.agh.hydra.common.util.CollectionUtils.mapList;
import static com.agh.hydra.common.util.ObjectUtils.getOrDefault;
import static com.agh.hydra.common.util.ValueObjectUtil.getValue;
import static java.util.Collections.singleton;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class ReferralService implements IReferralService {

    private final IPrivilegeService privilegeService;
    private final ReferralRepository referralRepository;
    private final IJobService jobService;

    @Override
    public void createReferralAnnouncement(@Valid @NotNull CreateReferralRequest request, @Valid @NotNull UserId userId) {
        privilegeService.throwIfUnprivileged(userId, FN_PRV_CREATE_REFERRAL);

        JobAnnouncement jobAnnouncement = getJobAnnouncement(request.getJobId());
        if (jobAnnouncement.getClosingDate().isBefore(request.getClosingDate())) {
            throw INVALID_REFERRAL_CLOSING_DATE.getException();
        }

        if (referralRepository.referralAnnouncementExists(getValue(userId), getValue(request.getJobId()))) {
            throw ACTIVE_REFERRAL_EXISTS.getException();
        }

        ReferralEntity entity = ReferralMapper.INSTANCE.mapCreateRequest(request);
        entity.setAuthorId(getValue(userId));

        referralRepository.createReferralAnnouncement(entity);
    }

    @Override
    public void invalidateReferralAnnouncement(@Valid @NotNull ReferralAnnouncementRequest request, @Valid @NotNull UserId userId) {
        privilegeService.throwIfUnprivileged(userId, FN_PRV_INVALIDATE_REFERRAL);
        referralRepository.invalidateReferralAnnouncement(mapList(request.getIds(), ValueObject::getValue));
    }

    @Override
    public Page<ReferralAnnouncement> getReferralAnnouncement(@Valid ReferralAnnouncementFilterRequest request,
                                                              @NotNull Pageable pageable) {
        ReferralAnnouncementFilter filter = getOrDefault(request, ReferralMapper.INSTANCE::mapFilter,
                new ReferralAnnouncementFilter());

        PageableUtils.setPageableParams(filter, pageable);

        List<ReferralAnnouncement> announcements =
                mapList(referralRepository.getReferralAnnouncement(filter), ReferralMapper.INSTANCE::mapAnnouncement);

        long total = referralRepository.getReferralAnnouncementCount(filter);

        return new PageImpl<>(announcements, pageable, total);
    }

    private JobAnnouncement getJobAnnouncement(JobId jobId) {
        JobAnnouncementFilterRequest filter =
                JobAnnouncementFilterRequest.builder().includeIds(singleton(jobId)).build();

        return jobService.getJobAnnouncements(filter, PageRequest.of(0, 1)).getContent().stream().findFirst()
                .orElseThrow(() -> JOB_ANNOUNCEMENT_NOT_FOUND.throwWith(jobId));
    }
}
