package com.helperserver.service.impl;

import com.helperserver.bean.ResourceDownload;
import com.helperserver.mapper.ResourceDownloadMapper;
import com.helperserver.service.ResourceDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ResourceDownloadServiceImpl implements ResourceDownloadService {

    @Autowired
    private ResourceDownloadMapper resourceDownloadMapper;

    @Override
    public ResourceDownload getDownloadById(String downloadId) {
        return resourceDownloadMapper.selectById(downloadId);
    }

    @Override
    public List<ResourceDownload> getDownloadsByResourceId(String resourceId) {
        return resourceDownloadMapper.selectByResourceId(resourceId);
    }

    @Override
    public List<ResourceDownload> getDownloadsByUserId(String userId) {
        return resourceDownloadMapper.selectByUserId(userId);
    }

    @Override
    public boolean createDownload(ResourceDownload resourceDownload) {
        if (resourceDownload.getDownloadId() == null) {
            resourceDownload.setDownloadId(UUID.randomUUID().toString());
        }
        if (resourceDownload.getDownloadCount() == null) {
            resourceDownload.setDownloadCount(0);
        }
        LocalDateTime now = LocalDateTime.now();
        resourceDownload.setCreatedAt(now);
        resourceDownload.setUpdatedAt(now);
        return resourceDownloadMapper.insert(resourceDownload) > 0;
    }

    @Override
    public boolean updateDownload(ResourceDownload resourceDownload) {
        resourceDownload.setUpdatedAt(LocalDateTime.now());
        return resourceDownloadMapper.update(resourceDownload) > 0;
    }

    @Override
    public boolean incrementDownloadCount(String downloadId) {
        return resourceDownloadMapper.incrementDownloadCount(downloadId) > 0;
    }

    @Override
    public boolean deleteDownload(String downloadId) {
        return resourceDownloadMapper.deleteById(downloadId) > 0;
    }
}