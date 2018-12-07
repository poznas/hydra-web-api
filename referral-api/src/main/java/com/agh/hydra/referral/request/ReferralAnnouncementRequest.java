package com.agh.hydra.referral.request;

import com.agh.hydra.referral.model.ReferralId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReferralAnnouncementRequest {

    /**
     * Referral announcement identifiers
     */
    @Valid
    @Size(min = 1)
    private Set<ReferralId> ids;
}
