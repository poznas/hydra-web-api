package com.agh.hydra.register;


import com.agh.hydra.api.register.request.UpdateCompaniesRequest;
import com.agh.hydra.api.register.service.ICompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.agh.hydra.register.RegisterController.REGISTER_CONTEXT;

@Slf4j
@Validated
@RestController
@RequestMapping(REGISTER_CONTEXT)
@RequiredArgsConstructor
public class RegisterController {

    static final String REGISTER_CONTEXT = "/register";
    private static final String COMPANY_UPDATE = "/company/update";

    private final ICompanyService companyService;


    @PostMapping(COMPANY_UPDATE)
    public void updateCompany(@Valid @NotNull @RequestBody UpdateCompaniesRequest request){
        companyService.updateCompanies(request);
    }
}
