package com.liming.licensebuilder.entity;

import lombok.Data;
import lombok.ToString;

/**
 * License校验类需要的参数
 */
@Data
@ToString
public class LicenseVerifyParam {

    /**
     * 证书subject
     */
    private String subject;

    /**
     * 公钥别称
     */
    private String publicAlias;

    /**
     * 访问公钥库的密码
     */
    private String storePass;

    /**
     * 证书生成路径
     */
    private String licensePath;

    /**
     * 密钥库存储路径
     */
    private String publicKeysStorePath;


}
