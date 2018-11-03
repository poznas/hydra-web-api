package com.agh.hydra.register.dao;

import com.agh.hydra.register.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {

    void updateUser(UserEntity user);
}
