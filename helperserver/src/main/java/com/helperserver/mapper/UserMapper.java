package com.helperserver.mapper;

import com.helperserver.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {
    User selectById(@Param("userId") String userId);
    User selectByUsername(String username);
    User selectByEmail(String email);
    User selectByPhone(String phone);
    List<User> selectAll();
    void insert(User user);
    void update(User user);
    void deleteById(@Param("userId") String userId);
}