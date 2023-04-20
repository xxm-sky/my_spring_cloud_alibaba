package com.xxm.config;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.xxm.bean.UserInfo;


/**
 * 保存登陆信息(后续待扩展)
 **/
public class LoginInfo {

    private static final ThreadLocal<UserInfo> LOCAL = new TransmittableThreadLocal<>();

    /**
     * 清除缓存
     */
    public static void clear() {
        LOCAL.remove();
    }

    public static UserInfo getLoginInfo() {
        return LOCAL.get();
    }

    public static void setLoginInfo(UserInfo info) {
        LOCAL.set(info);
    }

    public static Long getUserId() {
        return getLoginInfo().getUserId();
    }
    public static Long getMerchantId() {
        return getLoginInfo().getMerchantId();
    }

    public static String getUserName() {
        return getLoginInfo().getUserName();
    }

    public static String getUserType() {
        return getLoginInfo().getAuthType();
    }

    public static String getMobile() {
        return getLoginInfo().getMobile();
    }

    public static Integer getProvinceId() {
        return getLoginInfo().getProvinceId();
    }
}
