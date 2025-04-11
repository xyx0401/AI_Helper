package com.helperserver.service.impl;

import com.helperserver.bean.Resource;
import com.helperserver.mapper.ResourceMapper;
import com.helperserver.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public Resource uploadResource(MultipartFile file, String conferenceId, String agendaId) {
        throw new UnsupportedOperationException("文件上传功能已禁用");
    }

    @Override
    public Resource getResourceById(String resourceId) {
        return resourceMapper.selectById(resourceId);
    }

    @Override
    public List<Resource> getResourcesByConferenceId(String conferenceId) {
        return resourceMapper.selectByConferenceId(conferenceId);
    }

    @Override
    public List<Resource> getResourcesByAgendaId(String agendaId) {
        return resourceMapper.selectByAgendaId(agendaId);
    }

    @Override
    public void deleteResource(String resourceId) {
        resourceMapper.deleteById(resourceId);
    }
}