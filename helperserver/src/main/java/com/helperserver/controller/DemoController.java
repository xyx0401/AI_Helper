package com.helperserver.controller;

import com.helperserver.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
@Api(tags = "演示接口")
public class DemoController {
    @GetMapping
    @ApiOperation("测试连接")
    public Result<String> demo() {
        return Result.success("Spring Boot后端连接成功！");
    }
}