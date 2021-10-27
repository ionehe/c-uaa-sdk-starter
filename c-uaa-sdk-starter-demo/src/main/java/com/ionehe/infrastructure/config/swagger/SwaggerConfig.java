package com.ionehe.infrastructure.config.swagger;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Copyright (c) 2020 ionehe.com
 * Date: 2020/9/15
 * Time: 下午10:58
 *
 * @author 2020年 <a href="mailto:a@ionehe.com">秀</a>
 *
 * Swagger API文档相关配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.ionehe.application.rest")
                .title("权限框架demo")
                .description("权限框架demo相关接口文档")
                .contactName("秀")
                .contactEmail("a@ione.com")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
