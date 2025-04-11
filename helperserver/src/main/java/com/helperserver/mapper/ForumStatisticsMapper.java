package com.helperserver.mapper;

import com.helperserver.bean.ForumStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

import java.util.List;

@Mapper
public interface ForumStatisticsMapper {
    ForumStatistics selectById(@Param("statisticsId") String statisticsId);

    ForumStatistics selectByForumId(@Param("forumId") String forumId);

    List<ForumStatistics> selectTopByViewCount(@Param("limit") int limit);

    List<ForumStatistics> selectTopByFavoriteCount(@Param("limit") int limit);

    List<ForumStatistics> selectTopByCommentCount(@Param("limit") int limit);

    void insert(ForumStatistics statistics);

    void update(ForumStatistics statistics);

    void deleteById(@Param("statisticsId") String statisticsId);

    void incrementViewCount(@Param("forumId") String forumId, @Param("updatedAt") LocalDateTime updatedAt);

    void incrementFavoriteCount(@Param("forumId") String forumId, @Param("updatedAt") LocalDateTime updatedAt);

    void incrementCommentCount(@Param("forumId") String forumId, @Param("updatedAt") LocalDateTime updatedAt);

    void incrementShareCount(@Param("forumId") String forumId, @Param("updatedAt") LocalDateTime updatedAt);
}