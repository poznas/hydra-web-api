package com.agh.hydra.job.request;

import com.agh.hydra.common.model.JobId;
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
public class JobAnnouncementRequest {

    /**
     * Job announcement identifiers
     */
    @Valid
    @Size(min = 1)
    private Set<JobId> ids;
}
