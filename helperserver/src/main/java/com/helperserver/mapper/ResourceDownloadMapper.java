package com.helperserver.mapper;

import com.helperserver.bean.ResourceDownload;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ResourceDownloadMapper {
    ResourceDownload selectById(@Param("downloadId") String downloadId);
    
    List<ResourceDownload> selectByResourceId(@Param("resourceId") String resourceId);
    
    List<ResourceDownload> selectByUserId(@Param("userId") String userId);
    
    int insert(ResourceDownload resourceDownload);
    
    int update(ResourceDownload resourceDownload);
    
    int incrementDownloadCount(@Param("downloadId") String downloadId);
    
    int deleteById(@Param("downloadId") String downloadId);
}