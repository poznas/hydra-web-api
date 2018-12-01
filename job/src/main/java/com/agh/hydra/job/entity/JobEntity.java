package com.agh.hydra.job.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JobEntity {

    /**
     * Database identifier
     */
    private Long id;

    /**
     * Company identifier
     */
    private String companyId;

    /**
     * Job description
     */
    private String description;

    /**
     * programming languages
     */
    private String programmingLanguages;

    /**
     * Minimal salary
     */
    private Long minSalary;

    /**
     * Maximum salary
     */
    private Long maxSalary;

    /**
     * Closing date
     */
    private Instant closingDate;

    /**
     * Indicates whether job announcement record is active (not disabled)
     */
    private Boolean active;
}
