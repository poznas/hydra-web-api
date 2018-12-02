package com.agh.hydra.job.request;

import com.agh.hydra.common.model.CompanyId;
import com.agh.hydra.common.model.InformationContent;
import com.agh.hydra.common.model.JobTitle;
import com.agh.hydra.common.model.ProgrammingLanguage;
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
public class CreateJobAnnouncementRequest {

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
    @NotNull
    private Instant closingDate;
}
