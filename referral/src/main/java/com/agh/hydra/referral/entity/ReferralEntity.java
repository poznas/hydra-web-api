package com.agh.hydra.referral.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReferralEntity {

    /**
     * Database identifier
     */
    private Long referralId;

    /**
     * Job announcement identifier
     */
    private Long jobId;

    /**
     * Author user identifier
     */
    private String authorId;

    /**
     * Referral description
     */
    private String description;

    /**
     * Referral bonus
     */
    private Long referralBonus;

    /**
     * Referral bonus percentage
     */
    private Double referralBonusPercentage;

    /**
     * Closing date
     */
    private Instant closingDate;

    /**
     * Indicates whether referral announcement is active
     */
    private Boolean active;

}
