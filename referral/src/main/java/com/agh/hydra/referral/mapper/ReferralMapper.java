package com.agh.hydra.referral.mapper;

import com.agh.hydra.common.mapper.BaseMapper;
import com.agh.hydra.referral.entity.ReferralEntity;
import com.agh.hydra.referral.request.CreateReferralRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@SuppressWarnings("squid:S1214")
@Mapper(config = BaseMapper.class)
public interface ReferralMapper extends BaseMapper {

    ReferralMapper INSTANCE = Mappers.getMapper(ReferralMapper.class);

    @Mappings({
            @Mapping(target = "referralId", ignore = true),
            @Mapping(target = "authorId", ignore = true),
            @Mapping(target = "active", ignore = true)})
    ReferralEntity mapCreateRequest(CreateReferralRequest request);
}
