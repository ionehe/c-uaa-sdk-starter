package com.ionehe.infrastructure.repository.module;

import lombok.Data;

import java.util.Date;

/**
 * Copyright (c) 2020 ionehe.com
 * Date: 2020/11/24
 * Time: 下午05:25
 *
 * @author 2020年 <a href="mailto:a@ionehe.com">秀</a>
 */
@Data
public class Resource {
    /**
     * 主键
     */
    private Long id;

    /**
     * 父id
     */
    private Long pid;

    /**
     * 功能名称
     */
    private String name;

    /**
     * 后端为接口，前端为跳转url
     */
    private String url;

    /**
     * 类型（目录1，页面2，按钮3）
     */
    private Integer type;

    /**
     * 排序（正序）
     */
    private Integer sort;

    /**
     * 状态（1上架，2下架）
     */
    private Integer status;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 是否删除（删除1，不删除0）
     */
    private Boolean isDelete;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;
}