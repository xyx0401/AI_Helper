package com.helperserver.service;

import com.helperserver.bean.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ResourceService {
    /**
     * 上传会议资料
     * @param file 文件
     * @param conferenceId 会议ID
     * @param agendaId 议程ID
     * @return 资料信息
     */
    Resource uploadResource(MultipartFile file, String conferenceId, String agendaId);

    /**
     * 根据资料ID获取资料
     * @param resourceId 资料ID
     * @return 资料信息
     */
    Resource getResourceById(String resourceId);

    /**
     * 根据会议ID获取相关资料
     * @param conferenceId 会议ID
     * @return 资料列表
     */
    List<Resource> getResourcesByConferenceId(String conferenceId);

    /**
     * 根据议程ID获取相关资料
     * @param agendaId 议程ID
     * @return 资料列表
     */
    List<Resource> getResourcesByAgendaId(String agendaId);

    /**
     * 删除资料
     * @param resourceId 资料ID
     */
    void deleteResource(String resourceId);
}