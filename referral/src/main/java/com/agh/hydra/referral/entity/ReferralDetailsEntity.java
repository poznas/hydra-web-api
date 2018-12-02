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
}
