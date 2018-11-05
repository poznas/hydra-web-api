package com.agh.hydra.wiki.entity;

import com.agh.hydra.common.model.ProgrammingLanguage;
import com.agh.hydra.common.model.RecruitmentType;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentInfoEntity {

    /**
     * Database identifier
     */
    private Long id;

    /**
     * Author user identifier
     */
    private String authorId;

    /**
     * company identifier
     */
    private String companyId;

    /**
     * Recruitment type
     */
    private RecruitmentType recruitmentType;

    /**
     * Programming language
     */
    private ProgrammingLanguage programmingLanguage;

    /**
     * Information content
     */
    private String content;

    /**
     * Indicates whether recruitment information record is active (not disabled)
     */
    private Boolean active;

    /**
     * Up votes count
     */
    private int upVotes;

    /**
     * Down votes count
     */
    private int downVotes;
}
