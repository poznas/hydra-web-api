package com.agh.hydra.wiki.service;

import com.agh.hydra.common.model.UserId;
import com.agh.hydra.wiki.model.InformationDetails;
import com.agh.hydra.wiki.request.BaseInformationRequest;
import com.agh.hydra.wiki.request.CreateRecruitmentInfoRequest;
import com.agh.hydra.wiki.request.RecruitmentInformationFilterRequest;
import com.agh.hydra.wiki.request.VoteRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    /**
     * Disables recruitment information entry
     * @param request request with info entry identifiers
     * @param userId user identifier
     */
    void invalidateRecruitmentInformation(@Valid @NotNull BaseInformationRequest request,
                                          @Valid @NotNull UserId userId);

    /**
     * Creates vote entity assigned to information
     * @param request request with info entry identifier and vote value
     * @param userId user identifier
     */
    void voteRecruitmentInformation(@Valid @NotNull VoteRequest request,
                                    @Valid @NotNull UserId userId);

    /**
     * @param request filter request
     * @param pageable pageable
     * @param userId viewer user identifier
     * @return filtered, paged and detailed information list
     */
    Page<InformationDetails> getRecruitmentInformation(@Valid RecruitmentInformationFilterRequest request,
                                                       @NotNull Pageable pageable, @Valid @NotNull UserId userId);
}
