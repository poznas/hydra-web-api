package com.agh.hydra.referral.request;

import com.agh.hydra.referral.model.ReferralId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReferralApplicationRequest {

    /**
     * Referral announcement identifier
     */
    @Valid
    @NotNull
    private ReferralId referralId;

    /**
     * Github repository URL
     */
    @NotBlank
    private String githubUrl;

    /**
     * LinkedIn profile URL
     */
    private String linkedinUrl;

    /**
     * CV file URL
     */
    @NotBlank
    private String cvUrl;
}
