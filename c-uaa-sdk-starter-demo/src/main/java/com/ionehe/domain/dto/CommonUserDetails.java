package com.ionehe.domain.dto;

import cn.hutool.core.collection.CollUtil;
import com.ionehe.infrastructure.repository.module.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2020 ionehe.com
 * Date: 2020/11/25
 * Time: 10:29 下午
 *
 * @author 2020年 <a href="mailto:a@ionehe.com">秀</a>
 *
 * SpringSecurity需要的用户详情
 */
public class CommonUserDetails implements UserDetails {

    private UserInfo user;
    private List<Resource> resourceList;
    public CommonUserDetails(UserInfo user, List<Resource> resourceList) {
        this.user = user;
        this.resourceList = resourceList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的角色
        if (CollUtil.isEmpty(resourceList)) {
            return null;
        }

        return resourceList.stream()
                .map(res ->new SimpleGrantedAuthority(res.getUrl()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus();
    }
}
