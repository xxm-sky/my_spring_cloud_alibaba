package com.xxm.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xxm.bean.TokenInfo;


public class JWTUtil {

    /**
     * 签名用的密钥
     */
    private static final String SIGNING_KEY = "qAWMbVKdkAcVaVuY";

    public static TokenInfo createAppJWT(String userId) {
        return createJWT(userId, "app:");
    }

    public static TokenInfo createMerchantJWT(String userId) {
        return createJWT(userId, "merchant:");
    }

    public static TokenInfo createManagerJWT(String userId) {
        return createJWT(userId, "manager:");
    }

    private static TokenInfo createJWT(String userId, String loginType) {
        String token = JWT.create()
                .withJWTId(userId)
                .withKeyId(loginType)
                .withIssuedAt(DateUtil.date())
                .sign(Algorithm.HMAC256(SIGNING_KEY));
        return TokenInfo.builder().token(token).tokenPath(loginType + userId).build();
    }

    public static TokenInfo parse(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SIGNING_KEY)).build().verify(token);
        return TokenInfo.builder().tokenPath(decodedJWT.getKeyId() + decodedJWT.getId()).build();
    }

}
