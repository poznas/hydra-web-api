package com.agh.hydra.wiki.impl;

import com.agh.hydra.api.register.service.IPrivilegeService;
import com.agh.hydra.common.model.UserId;
import com.agh.hydra.common.model.ValueObject;
import com.agh.hydra.common.util.PageableUtils;
import com.agh.hydra.wiki.dao.WikiRepository;
import com.agh.hydra.wiki.entity.VoteEntity;
import com.agh.hydra.wiki.mapper.WikiMapper;
import com.agh.hydra.wiki.model.InformationDetails;
import com.agh.hydra.wiki.model.RecruitmentInfoFilter;
import com.agh.hydra.wiki.request.BaseInformationRequest;
import com.agh.hydra.wiki.request.CreateRecruitmentInfoRequest;
import com.agh.hydra.wiki.request.RecruitmentInformationFilterRequest;
import com.agh.hydra.wiki.request.VoteRequest;
import com.agh.hydra.wiki.service.IWikiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static com.agh.hydra.common.model.FunctionalPrivilege.FN_PRV_CREATE_INFORMATION;
import static com.agh.hydra.common.model.FunctionalPrivilege.FN_PRV_INVALIDATE_RECRUITMENT_INFO;
import static com.agh.hydra.common.util.CollectionUtils.mapList;
import static com.agh.hydra.common.util.ObjectUtils.getOrDefault;
import static com.agh.hydra.common.util.ObjectUtils.getOrNull;
import static com.agh.hydra.common.util.ValueObjectUtil.getValue;
import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class WikiService implements IWikiService {

    private final WikiRepository wikiRepository;
    private final IPrivilegeService privilegeService;

    @Override
    public void createRecruitmentInformation(@Valid @NotNull CreateRecruitmentInfoRequest request,
                                             @Valid @NotNull UserId userId) {
        privilegeService.throwIfUnprivileged(userId, FN_PRV_CREATE_INFORMATION);

        var entity = WikiMapper.INSTANCE.mapCreateRequest(request);
        entity.setAuthorId(getValue(userId));

        wikiRepository.createInformation(entity);
    }

    @Override
    public void invalidateRecruitmentInformation(@Valid @NotNull BaseInformationRequest request,
                                                 @Valid @NotNull UserId userId) {
        privilegeService.throwIfUnprivileged(userId, FN_PRV_INVALIDATE_RECRUITMENT_INFO);
        wikiRepository.invalidateInformation(mapList(request.getIds(), ValueObject::getValue));
    }

    @Override
    public void voteRecruitmentInformation(@Valid @NotNull VoteRequest request, @Valid @NotNull UserId userId) {
        wikiRepository.updateInformationVote(getValue(userId), getValue(request.getInformationId()), getVote(request));
    }

    @Override
    public Page<InformationDetails> getRecruitmentInformation(@Valid RecruitmentInformationFilterRequest request,
                                                              @NotNull Pageable pageable, @Valid @NotNull UserId userId) {
        var filter = getOrDefault(request,
                WikiMapper.INSTANCE::mapFilterRequest, new RecruitmentInfoFilter());

        PageableUtils.setPageableParams(filter, pageable);

        List<InformationDetails> informationDetails =
                mapList(wikiRepository.getRecruitmentInformation(filter), WikiMapper.INSTANCE::mapInfoDetails);

        decorateUserVotes(informationDetails, userId);

        var total = wikiRepository.getInformationCount(filter);

        return new PageImpl<>(informationDetails, pageable, total);
    }

    private void decorateUserVotes(@NotNull List<InformationDetails> informationDetails, @NotNull UserId userId) {
        if(!isEmpty(informationDetails)){
            var informationIds = mapList(informationDetails, details -> getValue(details.getId()));
            var userVotes = wikiRepository.getUserVotes(getValue(userId), informationIds).stream()
                    .collect(Collectors.toMap(VoteEntity::getInformationId, VoteEntity::getVote));

            informationDetails.forEach(details -> details.setUserVote(userVotes.get(getValue(details.getId()))));
        }
    }

    private String getVote(@Valid @NotNull VoteRequest request) {
        return getOrNull(request.getVote(), Enum::name);
    }
}
