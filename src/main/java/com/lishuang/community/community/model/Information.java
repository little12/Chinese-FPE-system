package com.lishuang.community.community.model;

import lombok.Data;

@Data
public class Information {
    private Integer id;
    private String user;
    private String name;
    private String newName;
    private String sex;
    private String phone;
    private String email;
    private String gmtCreated;
    private String gmtModified;

}
