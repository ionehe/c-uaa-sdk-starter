package com.ionehe.group.common.user.c.sdk.uaa.resources;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.util.List;

/**
 * Copyright (c) 2020 ionehe.com
 * Date: 2020/10/24
 * Time: 下午4:33
 *
 * @author 2020年 <a href="mailto:a@ionehe.com">秀</a>
 *
 * yml文件解析类
 */
public class ConfigParamFactory extends DefaultPropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        if (resource == null) {
            return super.createPropertySource(name, null);
        }
        List<PropertySource<?>> sources = new YamlPropertySourceLoader().load(resource.getResource().getFilename(), resource.getResource());
        return sources.get(0);
    }
}