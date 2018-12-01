package com.agh.hydra.referral.service;

import com.agh.hydra.common.model.UserId;
import com.agh.hydra.referral.request.CreateReferralRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface IReferralService {

    /**
     * Creates referral announcement entry
     *
     * @param request create request
     * @param userId  creator identifier
     */
    void createReferralAnnouncement(@Valid @NotNull CreateReferralRequest request, @Valid @NotNull UserId userId);
}
