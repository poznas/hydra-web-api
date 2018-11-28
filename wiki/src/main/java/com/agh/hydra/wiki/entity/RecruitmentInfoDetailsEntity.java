package com.agh.hydra.wiki.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentInfoDetailsEntity extends RecruitmentInfoEntity {

    /**
     * Username
     */
    @Valid
    private String username;

    /**
     * User image URL address
     */
    private String userImageUrl;

    /**
     * Company name
     */
    @Valid
    private String companyName;

    /**
     * Author reliability ratio
     */
    private Double authorReliabilityRatio;
}
