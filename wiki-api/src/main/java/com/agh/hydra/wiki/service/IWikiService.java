package com.agh.hydra.wiki.service;

import com.agh.hydra.common.model.UserId;
import com.agh.hydra.wiki.request.CreateRecruitmentInfoRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface IWikiService {

    /**
     * Creates recruitment information entry
     * @param request create request
     * @param userId creator identifier
     */
    void createRecruitmentInformation(@Valid @NotNull CreateRecruitmentInfoRequest request,
                                      @Valid @NotNull UserId userId);
}
