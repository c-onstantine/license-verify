package com.liming.licensebuilder.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author ltf
 * @date 2021-07-20 16:18
 */
@Data
@ToString
public class LicenseCheckModel implements Serializable {
    private static final long serialVersionUID = -1958010993802034865L;
    /**
     * 可被允许的IP地址
     */
    private List<String> ipAddress;

    /**
     * 可被允许的MAC地址
     */
    private List<String> macAddress;

}
