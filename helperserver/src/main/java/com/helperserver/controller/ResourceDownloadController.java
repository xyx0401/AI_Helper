package com.helperserver.controller;

import com.helperserver.bean.ResourceDownload;
import com.helperserver.service.ResourceDownloadService;
import com.helperserver.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/resource")
@Api(tags = "资源下载接口")
public class ResourceDownloadController {

    @Autowired
    private ResourceDownloadService resourceDownloadService;

    @GetMapping("/download/{downloadId}")
    @ApiOperation("获取下载信息")
    public Result<ResourceDownload> getDownload(@PathVariable String downloadId) {
        try {
            ResourceDownload download = resourceDownloadService.getDownloadById(downloadId);
            return download != null ? Result.success(download) : Result.error("下载信息不存在");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    @ApiOperation("获取用户下载历史")
    public Result<List<ResourceDownload>> getUserDownloads(@PathVariable String userId) {
        try {
            List<ResourceDownload> downloads = resourceDownloadService.getDownloadsByUserId(userId);
            return Result.success(downloads);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/upload")
    @ApiOperation("上传资源")
    public Result<ResourceDownload> uploadResource(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") String userId,
            @RequestParam("resourceId") String resourceId) {
        
        ResourceDownload resourceDownload = new ResourceDownload();
        resourceDownload.setDownloadId(UUID.randomUUID().toString());
        resourceDownload.setUserId(userId);
        resourceDownload.setResourceId(resourceId);
        resourceDownload.setFileName(file.getOriginalFilename());
        resourceDownload.setFileType(file.getContentType());
        resourceDownload.setFileSize(file.getSize());
        resourceDownload.setDownloadCount(0);
        resourceDownload.setLastDownloadTime(LocalDateTime.now());
        
        try {
            // 实现文件存储逻辑，获取下载URL
            String downloadUrl = "/api/resource/download/" + resourceDownload.getDownloadId();
            resourceDownload.setDownloadUrl(downloadUrl);
            
            boolean success = resourceDownloadService.createDownload(resourceDownload);
            return success ? Result.success(resourceDownload) : Result.error("创建下载记录失败");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/download/{downloadId}")
    @ApiOperation("增加下载次数")
    public Result<Void> incrementDownloadCount(@PathVariable String downloadId) {
        try {
            boolean success = resourceDownloadService.incrementDownloadCount(downloadId);
            return success ? Result.success() : Result.error("下载记录不存在");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/download/{downloadId}")
    @ApiOperation("删除下载记录")
    public Result<Void> deleteDownload(@PathVariable String downloadId) {
        try {
            boolean success = resourceDownloadService.deleteDownload(downloadId);
            return success ? Result.success() : Result.error("下载记录不存在");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}