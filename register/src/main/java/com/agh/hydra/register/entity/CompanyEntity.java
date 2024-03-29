package com.agh.hydra.register.entity;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity {

    /**
     * Company identifier
     */
    private String companyId;

    /**
     * Company name
     */
    private String companyName;

    /**
     * Company address
     */
    private String address;

    /**
     * language
     */
    private String language;

    /**
     * Indicates whether company is active
     */
    private Boolean active;
}
