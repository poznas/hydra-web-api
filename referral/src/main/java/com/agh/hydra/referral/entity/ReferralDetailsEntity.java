package com.agh.hydra.referral.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReferralDetailsEntity extends ReferralEntity {

    /**
     * Username
     */
    private String username;

    /**
     * User image URL address
     */
    private String userImageUrl;

    /**
     * Job title
     */
    private String title;

    /**
     * City
     */
    private String city;

    /**
     * company identifier
     */
    private String companyId;

    /**
     * Company name
     */
    private String companyName;
}
