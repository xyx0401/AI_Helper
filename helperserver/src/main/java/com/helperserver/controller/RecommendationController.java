package com.helperserver.controller;

import com.helperserver.bean.Conference;
import com.helperserver.bean.UserPreference;
import com.helperserver.service.RecommendationService;
import com.helperserver.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendation")
@Api(tags = "推荐系统接口")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/preference/{userId}")
    @ApiOperation("获取用户偏好")
    public Result<UserPreference> getUserPreference(@PathVariable String userId) {
        UserPreference preference = recommendationService.getUserPreference(userId);
        return Result.success(preference);
    }

    @PostMapping("/preference")
    @ApiOperation("更新用户偏好")
    public Result<Void> updateUserPreference(@RequestBody UserPreference preference) {
        recommendationService.updateUserPreference(preference);
        return Result.success();
    }

    @GetMapping("/conferences/{userId}")
    @ApiOperation("获取推荐会议列表")
    public Result<List<Conference>> getRecommendedConferences(@PathVariable String userId) {
        List<Conference> conferences = recommendationService.getRecommendedConferences(userId);
        return Result.success(conferences);
    }

    @PostMapping("/interests")
    @ApiOperation("更新用户兴趣标签")
    public Result<Void> updateUserInterests(@RequestParam String userId,
                                          @RequestParam String conferenceId,
                                          @RequestParam String actionType) {
        recommendationService.updateUserInterests(userId, conferenceId, actionType);
        return Result.success();
    }

    @GetMapping("/similar-users/{userId}")
    @ApiOperation("获取相似兴趣的用户列表")
    public Result<List<UserPreference>> getSimilarUsers(@PathVariable String userId) {
        List<UserPreference> similarUsers = recommendationService.getSimilarUsers(userId);
        return Result.success(similarUsers);
    }
}