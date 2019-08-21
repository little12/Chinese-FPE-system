package com.lishuang.community.community.model;

import lombok.Data;

/**
 * 和数据库之间的传输
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;

}
