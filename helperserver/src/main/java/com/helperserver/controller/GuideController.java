package com.helperserver.controller;

import com.helperserver.bean.ConferenceGuide;
import com.helperserver.service.GuideService;
import com.helperserver.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guide")
@Api(tags = "会议指南接口")
public class GuideController {

    @Autowired
    private GuideService guideService;

    @GetMapping("/{conferenceId}")
    @ApiOperation("获取会议指南信息")
    public Result<ConferenceGuide> getGuide(@PathVariable String conferenceId) {
        try {
            ConferenceGuide guide = guideService.getGuide(conferenceId);
            return Result.success(guide);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping
    @ApiOperation("创建会议指南")
    public Result<ConferenceGuide> createGuide(@RequestBody ConferenceGuide guide) {
        try {
            ConferenceGuide createdGuide = guideService.createGuide(guide);
            return Result.success(createdGuide);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping
    @ApiOperation("更新会议指南信息")
    public Result<ConferenceGuide> updateGuide(@RequestBody ConferenceGuide guide) {
        try {
            ConferenceGuide updatedGuide = guideService.updateGuide(guide);
            return Result.success(updatedGuide);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}