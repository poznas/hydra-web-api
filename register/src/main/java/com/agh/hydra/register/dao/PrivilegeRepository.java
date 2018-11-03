package com.agh.hydra.register.dao;

import com.agh.hydra.common.model.FunctionalPrivilege;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PrivilegeRepository {

    List<FunctionalPrivilege> getPrivileges(@Param("userId") String userId);
}
