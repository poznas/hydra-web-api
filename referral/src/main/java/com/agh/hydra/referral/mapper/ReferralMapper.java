package com.agh.hydra.referral.mapper;

import com.agh.hydra.common.mapper.BaseMapper;
import com.agh.hydra.referral.entity.ReferralApplicationEntity;
import com.agh.hydra.referral.entity.ReferralDetailsEntity;
import com.agh.hydra.referral.entity.ReferralEntity;
import com.agh.hydra.referral.model.*;
import com.agh.hydra.referral.request.CreateReferralApplicationRequest;
import com.agh.hydra.referral.request.CreateReferralRequest;
import com.agh.hydra.referral.request.ReferralAnnouncementFilterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import static com.agh.hydra.common.util.ValueObjectUtil.getValue;
import static com.agh.hydra.common.util.ValueObjectUtil.valueObject;

@SuppressWarnings("squid:S1214")
@Mapper(config = BaseMapper.class)
public interface ReferralMapper extends BaseMapper {

    ReferralMapper INSTANCE = Mappers.getMapper(ReferralMapper.class);

    @Mappings({
            @Mapping(target = "referralId", ignore = true),
            @Mapping(target = "authorId", ignore = true),
            @Mapping(target = "active", ignore = true)})
    ReferralEntity mapCreateRequest(CreateReferralRequest request);

    @Mappings({
            @Mapping(target = "pageSize", ignore = true),
            @Mapping(target = "offset", ignore = true)})
    ReferralAnnouncementFilter mapFilter(ReferralAnnouncementFilterRequest request);

    ReferralAnnouncement mapAnnouncement(ReferralDetailsEntity entity);

    @Mappings({
            @Mapping(target = "applicationId", ignore = true),
            @Mapping(target = "userId", ignore = true),
            @Mapping(target = "username", ignore = true),
            @Mapping(target = "userImageUrl", ignore = true)})
    ReferralApplicationEntity mapApplication(CreateReferralApplicationRequest request);

    ReferralApplication mapApplication(ReferralApplicationEntity entity);

    default ReferralId mapRefId(Long id) {
        return valueObject(id, ReferralId::of);
    }

    default Long mapRefId(ReferralId id) {
        return getValue(id);
    }

    default ApplicationId mapAppId(Long id) {
        return valueObject(id, ApplicationId::of);
    }
}
