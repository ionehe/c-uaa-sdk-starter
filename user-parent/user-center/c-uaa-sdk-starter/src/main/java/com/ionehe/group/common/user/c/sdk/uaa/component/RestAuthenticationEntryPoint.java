package com.ionehe.group.common.user.c.sdk.uaa.component;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.ionehe.group.common.io.Response;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Copyright (c) 2020 ionehe.com
 * Date: 2020/10/22
 * Time: 下午5:52
 *
 * @author 2020年 <a href="mailto:a@ionehe.com">秀</a>
 * 自定义返回结果：未登录或登录过期
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(Response.unauthorized("抱歉，请先登录账号")));
        response.getWriter().flush();
    }
}
