package com.lishuang.community.community.dto;

import lombok.Data;

/**
 * 网络和网络之间的传输
 */
@Data
public class AccessTokenDTO {
    //两个以上参数的方法需要封装
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
