package com.lishuang.community.community.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 引用配置文件中的常量
 */
@Component
public class ConstantProperties {
    @Value("${com.encrypt.key}")
    private String key;

    public String getKey() {
        return key;
    }
}
