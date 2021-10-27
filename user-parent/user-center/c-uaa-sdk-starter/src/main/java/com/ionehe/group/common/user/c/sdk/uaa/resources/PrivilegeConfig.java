//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ionehe.group.common.user.c.sdk.uaa.resources;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Copyright (c) 2020 ionehe.com
 * Date: 2020/10/24
 * Time: 下午4:33
 *
 * @author 2020年 <a href="mailto:a@ionehe.com">秀</a>
 *
 * 权限文件配置类
 */
@Data
@Slf4j
@Component
@PropertySource(value = "classpath:privilege.yml", encoding = "utf-8", factory = ConfigParamFactory.class)
@ConfigurationProperties(prefix = PrivilegeConfig.PREFIX)
public class PrivilegeConfig {
    public static final String PREFIX = "privilege";

    private List<PrivilegeConfig.Url> commonAnonymous = new ArrayList<>();
    private List<PrivilegeConfig.Url> anonymous = new ArrayList<>();
    private List<PrivilegeConfig.Url> commonAuthentication = new ArrayList<>();
    private List<PrivilegeConfig.Url> authentication = new ArrayList<>();
    private List<PrivilegeConfig.AuthUrl> commonAuthorization = new ArrayList<>();
    private List<PrivilegeConfig.AuthUrl> authorization = new ArrayList<>();


    @PostConstruct
    public void init() {
        log.info("PrivilegeConfig[]init[]anonymous:{}, authentication:{}, authorization:{}", anonymous, authentication, authorization);
    }

    @Data
    public static class AuthUrl extends PrivilegeConfig.Url {
        private List<String> privileges = new ArrayList<>();
    }

    @Data
    public static class Url {
        private String path;
        private String methods;
    }
}
