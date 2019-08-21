package com.lishuang.community.community.model;

import lombok.Data;

@Data
public class Sign {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String token;
}
