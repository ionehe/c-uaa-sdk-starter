package com.ionehe.group.common.user.c.sdk.uaa.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Copyright (c) 2020 ionehe.com
 * Date: 2020/12/2
 * Time: 5:54 下午
 *
 * @author 2020年 <a href="mailto:a@ionehe.com">秀</a>
 */
@Component
public class UaaLoginUtil {
    public static String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }
        return null;
    }
}
