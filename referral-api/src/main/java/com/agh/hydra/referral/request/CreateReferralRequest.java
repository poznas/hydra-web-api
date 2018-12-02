package com.agh.hydra.referral.request;

import com.agh.hydra.common.model.InformationContent;
import com.agh.hydra.common.model.JobId;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateReferralRequest {

    /**
     * Job announcement identifier
     */
    @Valid
    @NotNull
    private JobId jobId;

    /**
     * Information content
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
    @DecimalMax("1.00")
    @Digits(integer = 1, fraction = 2)
    private Double referralBonusPercentage;

    /**
     * Closing date
     */
    @Future
    @NotNull
    private Instant closingDate;
}
