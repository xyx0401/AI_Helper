package com.helperserver.mapper;

import com.helperserver.bean.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ResourceMapper {
    /**
     * 插入资源记录
     * @param resource 资源信息
     * @return 影响的行数
     */
    int insert(Resource resource);

    /**
     * 根据资源ID查询资源
     * @param resourceId 资源ID
     * @return 资源信息
     */
    Resource selectById(@Param("resourceId") String resourceId);

    /**
     * 根据会议ID查询资源列表
     * @param conferenceId 会议ID
     * @return 资源列表
     */
    List<Resource> selectByConferenceId(@Param("conferenceId") String conferenceId);

    /**
     * 根据议程ID查询资源列表
     * @param agendaId 议程ID
     * @return 资源列表
     */
    List<Resource> selectByAgendaId(@Param("agendaId") String agendaId);

    /**
     * 根据资源ID删除资源
     * @param resourceId 资源ID
     * @return 影响的行数
     */
    int deleteById(@Param("resourceId") String resourceId);
}