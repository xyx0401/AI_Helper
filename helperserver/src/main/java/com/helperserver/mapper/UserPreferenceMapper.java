package com.helperserver.mapper;

import com.helperserver.bean.UserPreference;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserPreferenceMapper {
    void insert(UserPreference preference);

    void update(UserPreference preference);

    UserPreference findByUserId(@Param("userId") String userId);

    List<UserPreference> findByInterest(@Param("interest") String interest);

    void delete(@Param("userId") String userId);
}