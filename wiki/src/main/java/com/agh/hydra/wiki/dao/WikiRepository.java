package com.agh.hydra.wiki.dao;

import com.agh.hydra.wiki.entity.RecruitmentInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface WikiRepository {
    
    void createInformation(RecruitmentInfoEntity entity);

    void invalidateInformation(@Param("informationIds") List<Long> list);
}
