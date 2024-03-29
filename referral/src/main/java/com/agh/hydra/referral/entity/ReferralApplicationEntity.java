package com.agh.hydra.referral.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReferralApplicationEntity {

    /**
     * Database identifier
     */
    private Long applicationId;

    /**
     * Referral announcement identifier
     */
    private Long referralId;

    /**
     * applier identifier
     */
    private String userId;

    /**
     * Username
     */
    private String username;

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
