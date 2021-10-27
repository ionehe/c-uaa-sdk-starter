package com.ionehe.application.rest.test;

import com.ionehe.group.common.io.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Copyright (c) 2020 ionehe.com
 * Date: 2020/11/4
 * Time: 18:18
 *
 * @author 2020年 <a href="mailto:a@ionehe.com">秀</a>
 */
@RestController
@RequestMapping("/test")
@Slf4j
@Api(tags = "功能测试", value ="功能测试" )
public class TestController {

    @ApiOperation("列表查看权限：普通用户（xiu）Y /管理员用户（admin）Y")
    @GetMapping("/list")
    public Response<String> list() {
        return Response.yes("列表数据查看成功！");
    }

    @ApiOperation("数据新增权限：普通用户（xiu）N /管理员用户（admin）Y")
    @ApiImplicitParam(name = "num", value = "输入随机数字", dataType = "Long", example = "100", required = true)
    @PostMapping("/add")
    public Response<String> add(Long num) {
        return Response.yes("新增" + num + "成功！");
    }

    @ApiOperation("匿名访问权限：无需登陆账号")
    @GetMapping("/anonymous")
    public Response<String> anonymous() {
        return Response.yes("访问成功！");
    }

    @ApiOperation("登陆可访问权限：登陆账号即可")
    @GetMapping("/authentication")
    public Response<String> authentication() {
        return Response.yes("访问成功！");
    }
}
