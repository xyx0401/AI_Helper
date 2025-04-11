package com.helperserver.controller;

import com.helperserver.bean.ForumTopic;
import com.helperserver.service.ForumService;
import com.helperserver.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forum")
@Api(tags = "论坛议题接口")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @GetMapping("/hot-topics")
    @ApiOperation("获取热门论坛议题排行榜")
    public Result<List<ForumTopic>> getHotTopics(
            @ApiParam(value = "时间范围：today-今日, week-本周, all-全部", defaultValue = "all")
            @RequestParam(defaultValue = "all") String timeRange,
            @ApiParam(value = "返回数量限制", defaultValue = "10")
            @RequestParam(defaultValue = "10") int limit) {
        List<ForumTopic> topics = forumService.getHotTopics(timeRange, limit);
        return Result.success(topics);
    }

    @PostMapping("/{topicId}/view")
    @ApiOperation("增加论坛议题浏览次数")
    public Result<Void> incrementViewCount(
            @ApiParam("议题ID") @PathVariable String topicId) {
        forumService.incrementViewCount(topicId);
        return Result.success();
    }



    @PostMapping("/{topicId}/favorite")
    @ApiOperation("增加论坛议题收藏数")
    public Result<Void> incrementFavoriteCount(
            @ApiParam("议题ID") @PathVariable String topicId) {
        forumService.incrementFavoriteCount(topicId);
        return Result.success();
    }
}