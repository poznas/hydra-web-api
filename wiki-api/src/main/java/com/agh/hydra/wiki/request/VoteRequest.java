package com.agh.hydra.wiki.request;

import com.agh.hydra.common.model.InformationId;
import com.agh.hydra.wiki.model.Vote;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoteRequest {

    /**
     * Recruitment information entry identifier
     */
    @Valid
    @NotNull
    private InformationId informationId;

    /**
     * Vote
     */
    @ApiModelProperty(value = "empty invalidates existing vote", allowEmptyValue = true)
    private Vote vote;
}
