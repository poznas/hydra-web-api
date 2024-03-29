package com.agh.hydra.api.register.request;

import com.agh.hydra.api.register.model.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class UpdateCompaniesRequest {

    @Valid
    @Size(min = 1)
    private List<Company> companies;
}
