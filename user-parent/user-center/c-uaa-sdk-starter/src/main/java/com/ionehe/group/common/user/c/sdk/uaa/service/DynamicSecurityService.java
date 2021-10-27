package com.ionehe.group.common.user.c.sdk.uaa.service;

import org.springframework.security.access.ConfigAttribute;

import java.util.List;

/**
 * Copyright (c) 2020 ionehe.com
 * Date: 2020/10/22
 * Time: 下午5:52
 *
 * @author 2020年 <a href="mailto:a@ionehe.com">秀</a>
 * 动态权限相关业务类
 */
public interface DynamicSecurityService {
    /**
     * 加载资源ANT通配符和资源对应List
     */
    List<ConfigAttribute> loadDataSource();
}
