package com.agh.hydra.wiki.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentInfoDetailsEntity extends RecruitmentInfoEntity {

    /**
     * Username
     */
    private String username;

    /**
     * User image URL address
     */
    private String userImageUrl;

    /**
     * Company name
     */
    private String companyName;

    /**
     * Author reliability ratio
     */
    private Double authorReliabilityRatio;
}
