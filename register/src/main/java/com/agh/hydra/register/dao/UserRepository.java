package com.agh.hydra.register.dao;

import com.agh.hydra.register.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRepository {

    void updateUser(UserEntity user);

    boolean userExists(@Param("userId") String userId);
}
