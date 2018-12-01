package com.agh.hydra.referral.dao;

import com.agh.hydra.referral.entity.ReferralEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReferralRepository {

    void createReferralAnnouncement(ReferralEntity entity);

    boolean referralAnnouncementExists(@Param("authorId") String authorId, @Param("jobId") Long jobId);
}