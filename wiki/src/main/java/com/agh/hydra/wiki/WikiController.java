package com.agh.hydra.wiki;

import com.agh.hydra.common.documentation.BaseDocumentation;
import com.agh.hydra.common.documentation.BasePageDocumentation;
import com.agh.hydra.common.model.UserId;
import com.agh.hydra.wiki.model.InformationDetails;
import com.agh.hydra.wiki.request.BaseInformationRequest;
import com.agh.hydra.wiki.request.CreateRecruitmentInfoRequest;
import com.agh.hydra.wiki.request.RecruitmentInformationFilterRequest;
import com.agh.hydra.wiki.request.VoteRequest;
import com.agh.hydra.wiki.service.IWikiService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.agh.hydra.wiki.WikiController.WIKI_CONTEXT;

@Slf4j
@Validated
@RestController
@RequestMapping(WIKI_CONTEXT)
@RequiredArgsConstructor
public class WikiController {

    static final String WIKI_CONTEXT = "/wiki";
    private static final String WIKI_RECRUITMENT_PATH = "/recruitment" + "/info";
    private static final String WIKI_CREATE_INFO = WIKI_RECRUITMENT_PATH + "/add";
    private static final String WIKI_INVALIDATE_INFO = WIKI_RECRUITMENT_PATH + "/invalidate";
    private static final String WIKI_VOTE_INFO = WIKI_RECRUITMENT_PATH + "/vote";
    private static final String WIKI_RECRUITMENT_INFO_ENTRIES = WIKI_RECRUITMENT_PATH + "/entries";

    private final IWikiService wikiService;

    @BaseDocumentation
    @PostMapping(WIKI_CREATE_INFO)
    public void createRecruitmentInformation(@Valid @NotNull @RequestBody CreateRecruitmentInfoRequest request,
                                             @ApiIgnore @RequestAttribute UserId userId) {
        wikiService.createRecruitmentInformation(request, userId);
    }

    @BaseDocumentation
    @PostMapping(WIKI_INVALIDATE_INFO)
    public void invalidateRecruitmentInformation(@Valid @NotNull @RequestBody BaseInformationRequest request,
                                                 @ApiIgnore @RequestAttribute UserId userId) {
        wikiService.invalidateRecruitmentInformation(request, userId);
    }

    @BaseDocumentation
    @PostMapping(WIKI_VOTE_INFO)
    public void invalidateRecruitmentInformation(@Valid @NotNull @RequestBody VoteRequest request,
                                                 @ApiIgnore @RequestAttribute UserId userId) {
        wikiService.voteRecruitmentInformation(request, userId);
    }

    @BasePageDocumentation
    @PostMapping(WIKI_RECRUITMENT_INFO_ENTRIES)
    public Page<InformationDetails> getCompanies(@ApiParam @Valid @Nullable @RequestBody RecruitmentInformationFilterRequest request,
                                                 @ApiIgnore @PageableDefault Pageable pageable) {
        return wikiService.getRecruitmentInformation(request, pageable);
    }
}
