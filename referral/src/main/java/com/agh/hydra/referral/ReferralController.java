package com.agh.hydra.referral;

import com.agh.hydra.common.documentation.BaseDocumentation;
import com.agh.hydra.common.model.UserId;
import com.agh.hydra.referral.request.CreateReferralRequest;
import com.agh.hydra.referral.request.ReferralAnnouncementRequest;
import com.agh.hydra.referral.service.IReferralService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.agh.hydra.referral.ReferralController.REFERRAL_CONTEXT;

@Slf4j
@Validated
@RestController
@RequestMapping(REFERRAL_CONTEXT)
@RequiredArgsConstructor
public class ReferralController {

    static final String REFERRAL_CONTEXT = "/referral";
    private static final String REFERRAL_CREATE_ANNOUNCEMENT = "/add";
    private static final String REFERRAL_INVALIDATE_ANNOUNCEMENT = "/invalidate";

    private final IReferralService referralService;

    @BaseDocumentation
    @PostMapping(REFERRAL_CREATE_ANNOUNCEMENT)
    public void createReferralAnnouncement(@Valid @NotNull @RequestBody CreateReferralRequest request,
                                           @ApiIgnore @RequestAttribute UserId userId) {
        referralService.createReferralAnnouncement(request, userId);
    }

    @BaseDocumentation
    @PutMapping(REFERRAL_INVALIDATE_ANNOUNCEMENT)
    public void invalidateReferralAnnouncement(@Valid @NotNull @RequestBody ReferralAnnouncementRequest request,
                                               @ApiIgnore @RequestAttribute UserId userId) {
        referralService.invalidateReferralAnnouncement(request, userId);
    }
}
