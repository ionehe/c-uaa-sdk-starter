package com.ionehe.application.rest;

import com.google.common.base.Throwables;
import com.ionehe.group.common.io.Response;
import com.ionehe.group.common.user.c.sdk.uaa.utils.JwtTokenUtil;
import com.ionehe.application.vo.LoginUser;
import com.ionehe.domain.service.UserReadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2020 ionehe.com
 * Date: 2020/12/9
 * Time: 11:36 上午
 *
 * @author 2020年 <a href="mailto:a@ionehe.com">秀</a>
 */
@Slf4j
@RestController
@RequestMapping("/login")
@Api(tags = "登陆服务", value = "登陆服务api")
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserReadService userReadService;

    @ApiOperation(value = "登陆成功后返回token", notes = "用户1：admin/admin 用户2：xiu/xiu")
    @PostMapping
    public Response<Map<String, String>> login(@RequestBody LoginUser user) {
        String token;
        try {
            // 密码需要客户端加密后传递
            UserDetails userDetails = userReadService.loadUserByUsername(user.getUsername()).getData();
            passwordCheck(user.getPassword(), userDetails.getPassword());

            // 存储到spring security上下文中
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 生成token
            token = jwtTokenUtil.generateToken(userDetails);
            Map<String, String> tokenMap = new HashMap<>(2);
            tokenMap.put("tokenHead", "Bearer ");
            tokenMap.put("token", token);
            return Response.yes(tokenMap);
        } catch (AuthenticationException e) {
            log.warn("LoginController[]login[]error! cause:{}", e.getMessage());
            return Response.no(e.getMessage());
        } catch (Exception e) {
            log.error("LoginController[]login[]error! cause:{}", Throwables.getStackTraceAsString(e));
            return Response.no("登陆异常");
        }
    }

    /**
     * 密码校验
     *
     * @param pw       用户输入密码
     * @param password 数据库密码
     */
    private void passwordCheck(String pw, String password) {
        if (!passwordEncoder.matches(pw, password)) {
            throw new UsernameNotFoundException("用户名或密码不正确");
        }
    }
}
