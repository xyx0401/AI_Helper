package com.helperserver.service;

import com.helperserver.bean.ResourceDownload;

import java.util.List;

public interface ResourceDownloadService {
    /**
     * 根据下载ID获取下载记录
     */
    ResourceDownload getDownloadById(String downloadId);

    /**
     * 根据资源ID获取下载记录列表
     */
    List<ResourceDownload> getDownloadsByResourceId(String resourceId);

    /**
     * 根据用户ID获取下载记录列表
     */
    List<ResourceDownload> getDownloadsByUserId(String userId);

    /**
     * 创建下载记录
     */
    boolean createDownload(ResourceDownload resourceDownload);

    /**
     * 更新下载记录
     */
    boolean updateDownload(ResourceDownload resourceDownload);

    /**
     * 增加下载次数
     */
    boolean incrementDownloadCount(String downloadId);

    /**
     * 删除下载记录
     */
    boolean deleteDownload(String downloadId);
}