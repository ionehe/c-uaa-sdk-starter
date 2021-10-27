package com.ionehe.infrastructure.config.swagger;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Copyright (c) 2020 ionehe.com
 * Date: 2020/9/15
 * Time: 下午10:58
 *
 * @author 2020年 <a href="mailto:a@ionehe.com">秀</a>
 *
 * Swagger自定义配置
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class SwaggerProperties {
    /**
     * API文档生成基础路径
     */
    private String apiBasePackage;
    /**
     * 是否要启用登录认证
     */
    private boolean enableSecurity;
    /**
     * 文档标题
     */
    private String title;
    /**
     * 文档描述
     */
    private String description;
    /**
     * 文档版本
     */
    private String version;
    /**
     * 文档联系人姓名
     */
    private String contactName;
    /**
     * 文档联系人网址
     */
    private String contactUrl;
    /**
     * 文档联系人邮箱
     */
    private String contactEmail;
}
