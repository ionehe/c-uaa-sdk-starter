package com.ionehe.application.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Copyright (c) 2020 ionehe.com
 * Date: 2020/12/9
 * Time: 11:38 上午
 *
 * @author 2020年 <a href="mailto:a@ionehe.com">秀</a>
 */
@Data
public class LoginUser {

    @ApiModelProperty(value = "账号", example = "admin", required = true)
    private String username;

    @ApiModelProperty(value = "密码", example = "admin", required = true)
    private String password;
}
