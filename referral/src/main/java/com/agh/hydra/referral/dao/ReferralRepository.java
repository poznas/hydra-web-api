package com.agh.hydra.referral.dao;

import com.agh.hydra.referral.entity.ReferralApplicationEntity;
import com.agh.hydra.referral.entity.ReferralDetailsEntity;
import com.agh.hydra.referral.entity.ReferralEntity;
import com.agh.hydra.referral.model.ReferralAnnouncementFilter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotNull;
import java.util.List;

@Mapper
public interface ReferralRepository {

    void createReferralAnnouncement(ReferralEntity entity);

    boolean referralAnnouncementExists(@Param("authorId") String authorId, @Param("jobId") Long jobId);

    void invalidateReferralAnnouncement(@Param("ids") List<Long> ids);

    List<ReferralDetailsEntity> getReferralAnnouncement(ReferralAnnouncementFilter filter);

    long getReferralAnnouncementCount(ReferralAnnouncementFilter filter);

    boolean referralApplicationExists(@Param("userId") String userId, @Param("referralId") Long referralId);

    void createReferralApplication(ReferralApplicationEntity application);

    List<ReferralApplicationEntity> getReferralApplications(@NotNull @Param("referralId") Long referralId);
}
