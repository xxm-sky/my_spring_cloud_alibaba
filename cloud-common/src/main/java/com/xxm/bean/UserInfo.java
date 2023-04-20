package com.xxm.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private Long userId;

    private String userName;

    /**
     * app.app端登陆账号
     * merchant.商家端登陆账号
     * manager.管理端登陆账号
     */
    private String authType;

    private String mobile;

    private String token;

    /**
     * 省份ID
     */
    private Integer provinceId;

    /**
     * 商户Id/姓氏网站id
     */
    private Long merchantId;
}
