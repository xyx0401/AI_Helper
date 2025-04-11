package com.helperserver.mapper;

import com.helperserver.bean.UserAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserAuthMapper {
    UserAuth selectById(@Param("authId") String authId);

    UserAuth selectByUserIdAndType(@Param("userId") String userId, @Param("authType") String authType);

    UserAuth selectByIdentifierAndType(@Param("identifier") String identifier, @Param("authType") String authType);

    // 移除Insert注解，使用XML配置文件中的定义
    void insert(UserAuth userAuth);

    // 移除Update注解，使用XML配置文件中的定义
    void update(UserAuth userAuth);

    void deleteById(@Param("authId") String authId);

    List<UserAuth> selectByUserId(@Param("userId") String userId);
}