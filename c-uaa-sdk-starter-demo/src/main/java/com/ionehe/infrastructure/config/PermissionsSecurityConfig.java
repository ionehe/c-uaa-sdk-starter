package com.ionehe.infrastructure.config;

import com.ionehe.domain.service.UserReadService;
import com.ionehe.group.common.user.c.sdk.uaa.config.SecurityConfig;
import com.ionehe.group.common.user.c.sdk.uaa.service.DynamicSecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2020 ionehe.com
 * Date: 2020/9/15
 * Time: 下午10:58
 *
 * @author 2020年 <a href="mailto:a@ionehe.com">秀</a>
 * 【security模块相关配置】
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class PermissionsSecurityConfig extends SecurityConfig {
    @Autowired
    private UserReadService userReadService;

    /**
     * 获取用户信息（用户名+用户权限集合）
     *
     * @return 返回一个用户对象给spring security进行认证鉴权
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {

        return username -> userReadService.loadUserByUsername(username).getData();
    }

    /**
     * TODO
     * 加载需要鉴权的资源信息
     *
     * @return 需要鉴权的资源信息
     */
    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return () -> {
            // mock数据库录入的资源列表
            List<ConfigAttribute> list = mockConfigAttributeMap();
            log.info("PermissionsSecurityConfig[]resource:{}", list);
            return list;
        };
    }


    /**
     * mock数据库录入的资源列表
     *
     * @return 资源列表
     */
    private List<ConfigAttribute> mockConfigAttributeMap() {
        List<ConfigAttribute> list = new ArrayList<>(2);
        list.add(new org.springframework.security.access.SecurityConfig("/test/list"));
        list.add(new org.springframework.security.access.SecurityConfig("/test/add"));
        return list;
    }
}
