package com.agh.hydra.api.register.request;

import com.agh.hydra.api.register.model.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCompaniesRequest {

    @Valid
    @NotEmpty
    private List<Company> companies;
}
