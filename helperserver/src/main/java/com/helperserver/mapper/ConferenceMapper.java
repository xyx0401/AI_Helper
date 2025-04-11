package com.helperserver.mapper;

import com.helperserver.bean.Conference;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ConferenceMapper {
    int insert(Conference conference);

    int update(Conference conference);

    Conference selectById(@Param("conferenceId") String conferenceId);
    
    List<Conference> selectAll();

    List<Conference> selectByTitle(@Param("title") String title);
    
    List<Conference> selectByStartTimeAfter(@Param("startTime") java.util.Date startTime);
    
    List<Conference> selectByEndTimeBefore(@Param("endTime") java.util.Date endTime);
    
    List<Conference> selectByLocationId(@Param("locationId") String locationId);

    List<Conference> selectByTag(@Param("tag") String tag);

    List<Conference> findUpcoming(@Param("startTime") java.util.Date startTime);

    int deleteById(@Param("conferenceId") String conferenceId);
    
    List<Conference> findRecommendedByUserId(@Param("userId") String userId);
}