package com.agh.hydra.wiki.dao;

import com.agh.hydra.wiki.entity.RecruitmentInfoEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WikiRepository {
    
    void createInformation(RecruitmentInfoEntity entity);
}
