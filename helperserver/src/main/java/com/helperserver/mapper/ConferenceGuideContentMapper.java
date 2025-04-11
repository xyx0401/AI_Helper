package com.helperserver.mapper;

import com.helperserver.bean.ConferenceGuideContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ConferenceGuideContentMapper {
    ConferenceGuideContent selectById(@Param("guideId") String guideId);

    List<ConferenceGuideContent> selectByConferenceId(@Param("conferenceId") String conferenceId);

    List<ConferenceGuideContent> selectByConferenceIdAndType(@Param("conferenceId") String conferenceId, @Param("type") String type);

    void insert(ConferenceGuideContent guideContent);

    void update(ConferenceGuideContent guideContent);

    void deleteById(@Param("guideId") String guideId);

    void deleteByConferenceId(@Param("conferenceId") String conferenceId);
}