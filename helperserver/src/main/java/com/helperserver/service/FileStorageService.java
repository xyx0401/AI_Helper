package com.helperserver.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface FileStorageService {
    /**
     * 存储文件并返回访问URL
     */
    String storeFile(MultipartFile file, String resourceId) throws IOException;

    /**
     * 获取文件的存储路径
     */
    Path getFilePath(String resourceId, String fileName);

    /**
     * 删除文件
     */
    boolean deleteFile(String resourceId, String fileName);
}