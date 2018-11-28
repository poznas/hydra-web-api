package com.agh.hydra.wiki.dao;

import com.agh.hydra.wiki.entity.RecruitmentInfoDetailsEntity;
import com.agh.hydra.wiki.entity.RecruitmentInfoEntity;
import com.agh.hydra.wiki.entity.VoteEntity;
import com.agh.hydra.wiki.model.RecruitmentInfoFilter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WikiRepository {

    void createInformation(RecruitmentInfoEntity entity);

    void invalidateInformation(@Param("informationIds") List<Long> informationIds);

    void updateInformationVote(@Param("userId") String userId,
                               @Param("informationId") Long informationId,
                               @Param("vote") String vote);

    List<VoteEntity> getUserVotes(@Param("userId") String userId, @Param("informationIds") List<Long> informationIds);

    List<RecruitmentInfoDetailsEntity> getRecruitmentInformation(RecruitmentInfoFilter filter);

    long getInformationCount(RecruitmentInfoFilter filter);
}
