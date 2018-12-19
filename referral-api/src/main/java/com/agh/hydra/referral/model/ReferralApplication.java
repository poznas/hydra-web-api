package com.agh.hydra.referral.model;

import com.agh.hydra.common.model.UserId;
import com.agh.hydra.common.model.Username;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReferralApplication {

    /**
     * Database identifier
     */
    private ApplicationId applicationId;

    /**
     * Referral announcement identifier
     */
    private ReferralId referralId;

    /**
     * applier identifier
     */
    private UserId userId;

    /**
     * Username
     */
    private Username username;

    /**
     * User image URL address
     */
    private String userImageUrl;

    /**
     * Github repository URL
     */
    private String githubUrl;

    /**
     * LinkedIn profile URL
     */
    private String linkedinUrl;

    /**
     * CV file URL
     */
    private String cvUrl;
}
