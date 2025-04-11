package com.helperserver.service.impl;

import com.helperserver.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Override
    public String storeFile(MultipartFile file, String resourceId) {
        throw new UnsupportedOperationException("文件上传功能已禁用");
    }

    @Override
    public Path getFilePath(String resourceId, String fileName) {
        throw new UnsupportedOperationException("文件上传功能已禁用");
    }

    @Override
    public boolean deleteFile(String resourceId, String fileName) {
        throw new UnsupportedOperationException("文件上传功能已禁用");
    }
}