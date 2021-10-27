package com.ionehe.domain.service;

import com.google.common.collect.Lists;
import com.ionehe.domain.dto.CommonUserDetails;
import com.ionehe.domain.dto.UserInfo;
import com.ionehe.group.common.io.Response;
import com.ionehe.infrastructure.repository.module.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2021 ionehe.com
 * Date: 2021/5/10
 * Time: 2:22 下午
 *
 * @author 2021年 <a href="mailto:a@ionehe.com">秀</a>
 * 【用户Service】
 */
@Service
public class UserReadService {
    private List<UserInfo> userService;
    private Map<String, List<Resource>> resourceService;

    /**
     * TODO
     * 通过用户名加载用户信息
     * @param username 用户名
     * @return 用户信息
     */
    public Response<UserDetails> loadUserByUsername(String username) {

        return Response.yes(new CommonUserDetails(userService.stream().filter(u -> u.getName().equals(username)).findFirst().get(),
                getResourceList(username)));
    }

    // 以下为模拟用户信息mock代码，真实业务需自行编写

    @PostConstruct
    public void mockDbUser() {
        userService = new ArrayList<>();
        resourceService = new HashMap<>();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // mock两个用户
        UserInfo xiu = new UserInfo();
        xiu.setName("xiu");
        xiu.setAccount("xiu");
        xiu.setPassword(passwordEncoder.encode("xiu"));
        xiu.setStatus(true);
        userService.add(xiu);
        UserInfo admin = new UserInfo();
        admin.setName("admin");
        admin.setAccount("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setStatus(true);
        userService.add(admin);

        // mock用户的权限列表
        Resource xiuResource = new Resource();
        xiuResource.setUrl("/test/list");
        resourceService.put("xiu", Lists.newArrayList(xiuResource));
        Resource adminResource = new Resource();
        adminResource.setUrl("/test/add");
        Resource adminResource1 = new Resource();
        adminResource1.setUrl("/test/list");
        resourceService.put("admin", Lists.newArrayList(adminResource, adminResource1));
    }

    private List<Resource> getResourceList(String account) {
        return resourceService.get(account);
    }

}
