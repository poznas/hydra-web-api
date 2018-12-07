package com.agh.hydra.referral.model;

import com.agh.hydra.common.model.ValueObject;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value(staticConstructor = "of")
public class ReferralId implements ValueObject<Long> {

    @Min(0)
    @NotNull
    Long value;
}
