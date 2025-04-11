package com.helperserver.controller;

import com.helperserver.bean.Resource;
import com.helperserver.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
@Api(tags = "会议资料管理接口")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/{resourceId}")
    @ApiOperation("获取会议资料信息")
    public ResponseEntity<Resource> getResource(@PathVariable String resourceId) {
        Resource resource = resourceService.getResourceById(resourceId);
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/conference/{conferenceId}")
    @ApiOperation("获取会议相关的所有资料")
    public ResponseEntity<List<Resource>> getConferenceResources(@PathVariable String conferenceId) {
        List<Resource> resources = resourceService.getResourcesByConferenceId(conferenceId);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/agenda/{agendaId}")
    @ApiOperation("获取议程相关的所有资料")
    public ResponseEntity<List<Resource>> getAgendaResources(@PathVariable String agendaId) {
        List<Resource> resources = resourceService.getResourcesByAgendaId(agendaId);
        return ResponseEntity.ok(resources);
    }

    @DeleteMapping("/{resourceId}")
    @ApiOperation("删除会议资料")
    public ResponseEntity<Void> deleteResource(@PathVariable String resourceId) {
        resourceService.deleteResource(resourceId);
        return ResponseEntity.ok().build();
    }
}