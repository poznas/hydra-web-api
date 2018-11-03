package com.agh.hydra.register.dao;

import com.agh.hydra.common.model.FunctionalPrivilege;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface PrivilegeRepository {

    List<FunctionalPrivilege> getPrivileges(@Param("userId") String userId);

    boolean hasPrivilege(@Param("userId") String userId, @Param("privilege") String privilege);

    void addPrivileges(@Param("userId") String userId, @Param("privileges") Set<FunctionalPrivilege> privileges);
}
