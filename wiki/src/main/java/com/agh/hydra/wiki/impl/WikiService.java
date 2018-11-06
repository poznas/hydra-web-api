package com.agh.hydra.wiki.impl;

import com.agh.hydra.api.register.service.IPrivilegeService;
import com.agh.hydra.common.model.UserId;
import com.agh.hydra.common.model.ValueObject;
import com.agh.hydra.wiki.dao.WikiRepository;
import com.agh.hydra.wiki.entity.RecruitmentInfoEntity;
import com.agh.hydra.wiki.mapper.WikiMapper;
import com.agh.hydra.wiki.request.BaseInformationRequest;
import com.agh.hydra.wiki.request.CreateRecruitmentInfoRequest;
import com.agh.hydra.wiki.service.IWikiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.agh.hydra.common.model.FunctionalPrivilege.FN_PRV_CREATE_INFORMATION;
import static com.agh.hydra.common.model.FunctionalPrivilege.FN_PRV_INVALIDATE_RECRUITMENT_INFO;
import static com.agh.hydra.common.util.CollectionUtils.mapList;
import static com.agh.hydra.common.util.ValueObjectUtil.getValue;

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

        RecruitmentInfoEntity entity = WikiMapper.INSTANCE.mapCreateRequest(request);
        entity.setAuthorId(getValue(userId));

        wikiRepository.createInformation(entity);
    }

    @Override
    public void invalidateRecruitmentInformation(@Valid @NotNull BaseInformationRequest request,
                                                 @Valid @NotNull UserId userId) {
        privilegeService.throwIfUnprivileged(userId, FN_PRV_INVALIDATE_RECRUITMENT_INFO);
        wikiRepository.invalidateInformation(mapList(request.getIds(), ValueObject::getValue));
    }
}
