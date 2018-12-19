package com.agh.hydra.referral.service;

import com.agh.hydra.api.register.model.User;
import com.agh.hydra.common.model.UserId;
import com.agh.hydra.referral.model.ReferralAnnouncement;
import com.agh.hydra.referral.model.ReferralApplication;
import com.agh.hydra.referral.model.ReferralId;
import com.agh.hydra.referral.request.CreateReferralApplicationRequest;
import com.agh.hydra.referral.request.CreateReferralRequest;
import com.agh.hydra.referral.request.ReferralAnnouncementFilterRequest;
import com.agh.hydra.referral.request.ReferralAnnouncementRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface IReferralService {

    /**
     * Creates referral announcement entry
     *
     * @param request create request
     * @param userId  creator identifier
     */
    void createReferralAnnouncement(@Valid @NotNull CreateReferralRequest request, @Valid @NotNull UserId userId);

    /**
     * Disables referral announcement entry
     *
     * @param request request with referral announcement identifiers
     * @param userId  user identifier
     */
    void invalidateReferralAnnouncement(@Valid @NotNull ReferralAnnouncementRequest request,
                                        @Valid @NotNull UserId userId);

    /**
     * @param request  filter request
     * @param pageable pageable
     * @return filtered, paged and detailed referral announcement list
     */
    Page<ReferralAnnouncement> getReferralAnnouncement(@Valid ReferralAnnouncementFilterRequest request,
                                                       @NotNull Pageable pageable);

    /**
     * Creates referral announcement application
     *
     * @param request apply request
     * @param userId  applier identifier
     */
    void applyForReferralAnnouncement(@Valid @NotNull CreateReferralApplicationRequest request,
                                      @Valid @NotNull UserId userId);

    /**
     * Get referral application list
     *
     * @param referralId referral announcement identifier
     * @param userId     author identifier
     */
    List<ReferralApplication> getReferralApplications(@Valid @NotNull ReferralId referralId,
                                                      @Valid @NotNull UserId userId);

    /**
     * Get referral applier list
     *
     * @param referralId referral announcement identifier
     */
    List<User> getReferralAppliers(@Valid @NotNull ReferralId referralId);
}
