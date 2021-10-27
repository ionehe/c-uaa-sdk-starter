package com.ionehe.group.common.user.c.sdk.uaa.component;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.URLUtil;
import com.ionehe.group.common.user.c.sdk.uaa.service.DynamicSecurityService;
import com.ionehe.group.common.user.c.sdk.uaa.resources.PrivilegeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Copyright (c) 2020 ionehe.com
 * Date: 2020/10/22
 * Time: 下午5:52
 *
 * @author 2020年 <a href="mailto:a@ionehe.com">秀</a>
 * 动态权限数据源，用于获取动态权限规则
 */
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static List<ConfigAttribute> configAttributeList = null;
    @Autowired
    private DynamicSecurityService dynamicSecurityService;
    @Autowired
    private PrivilegeConfig privilegeConfig;

    @PostConstruct
    public void loadDataSource() {
        configAttributeList = dynamicSecurityService.loadDataSource();
    }

    public void clearDataSource() {
        configAttributeList.clear();
        configAttributeList = null;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if (configAttributeList == null) this.loadDataSource();

        List<ConfigAttribute> configAttributes = new ArrayList<>();

        //获取当前访问的路径
        String url = ((FilterInvocation) o).getRequestUrl();
        String path = URLUtil.getPath(url);

        PathMatcher pathMatcher = new AntPathMatcher();

        // 如果是登陆可访问直接返回
        List<PrivilegeConfig.Url> logInToAccess = privilegeConfig.getAuthentication();

        boolean flag = false;
        if (CollUtil.isNotEmpty(logInToAccess)) {
            for (PrivilegeConfig.Url a : logInToAccess) {
                if (!pathMatcher.match(a.getPath(), path)) continue;

                flag = true;
            }
        }

        if (flag) return configAttributes;

        //获取访问该路径所需资源
        Iterator<ConfigAttribute> iterator = configAttributeList.iterator();

        //匹配是否是需要授权的路径
        while (iterator.hasNext()) {
            ConfigAttribute next = iterator.next();
            String pattern = next.getAttribute();
            if (pathMatcher.match(pattern, path)) {
                configAttributes.add(next);

                flag = true;
            }
        }

        if (!flag) {
            throw new AccessDeniedException("抱歉，您没有访问权限");
        }

        // 未设置操作请求权限，返回空集合
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
