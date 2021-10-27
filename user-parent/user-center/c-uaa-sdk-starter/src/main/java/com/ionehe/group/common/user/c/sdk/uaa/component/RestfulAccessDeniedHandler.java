package com.ionehe.group.common.user.c.sdk.uaa.component;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.ionehe.group.common.io.Response;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

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
 * 自定义返回结果：没有权限访问时
 */
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.HTTP_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(Response.forbidden(e.getMessage())));
        response.getWriter().flush();
    }
}
