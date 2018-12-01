package com.agh.hydra.job.model;

import com.agh.hydra.common.model.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobAnnouncement {

    /**
     * Database identifier
     */
    @Valid
    @NotNull
    private JobId jobId;

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
    private CompanyName companyName;

    /**
     * City
     */
    @NotBlank
    private String city;

    /**
     * Information content
     */
    @Valid
    @NotNull
    private InformationContent description;

    /**
     * Programming language
     */
    private Set<ProgrammingLanguage> programmingLanguages;

    /**
     * Minimal salary
     */
    @NotNull
    private Long minSalary;

    /**
     * Maximum salary
     */
    @NotNull
    private Long maxSalary;

    /**
     * Closing date
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Instant closingDate;
}
