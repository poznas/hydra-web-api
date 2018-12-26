package com.agh.hydra.referral.model;

import com.agh.hydra.common.model.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReferralAnnouncement {

    /**
     * Database identifier
     */
    @Valid
    @NotNull
    private ReferralId referralId;

    /**
     * Job announcement identifier
     */
    @Valid
    @NotNull
    private JobId jobId;

    /**
     * Author user identifier
     */
    @Valid
    @NotNull
    private UserId authorId;

    /**
     * Referral description
     */
    @Valid
    @NotNull
    private InformationContent description;

    /**
     * Referral bonus
     */
    @NotNull
    private Long referralBonus;

    /**
     * Referral bonus percentage
     */
    @NotNull
    private Double referralBonusPercentage;

    /**
     * Closing date
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Instant closingDate;

    /**
     * Username
     */
    @Valid
    @NotNull
    private Username username;

    /**
     * User image URL address
     */
    private String userImageUrl;

    /**
     * Job title
     */
    @Valid
    @NotNull
    private JobTitle title;

    /**
     * company identifier
     */
    @Valid
    @NotNull
    private CompanyId companyId;

    /**
     * Company name
     */
    @Valid
    @NotNull
    private CompanyName companyName;

    /**
     * City
     */
    @NotBlank
    private String city;

}
