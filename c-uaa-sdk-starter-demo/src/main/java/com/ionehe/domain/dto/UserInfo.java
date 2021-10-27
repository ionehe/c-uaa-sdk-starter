package com.ionehe.domain.dto;

import lombok.Data;

/**
 * Copyright (c) 2021 ionehe.com
 * Date: 2021/5/10
 * Time: 2:10 下午
 *
 * @author 2021年 <a href="mailto:a@ionehe.com">秀</a>
 * 【用户实体类】
 */
@Data
public class UserInfo {
    /**
     * 姓名
     */
    private String name;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态（false冻结，true启用）
     */
    private Boolean status;
}
