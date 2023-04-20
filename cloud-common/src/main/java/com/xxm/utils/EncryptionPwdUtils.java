package com.xxm.utils;

import java.nio.charset.StandardCharsets;

/**
 * 密码加密显示
 */
public class EncryptionPwdUtils {

    public static String EncryptionPwd(String pwd) {
        StringBuilder stringBuilder = new StringBuilder();
        if (pwd.length() >= 6) {
            byte[] bytes = pwd.getBytes(StandardCharsets.UTF_8);
            stringBuilder.append(bytes[0]);
            stringBuilder.append(bytes[1]);
            for (int i = 0; i < 6; i++) {
                stringBuilder.append("*");
            }
            stringBuilder.append(bytes[stringBuilder.length() - 1]);
            stringBuilder.append(bytes[stringBuilder.length() - 2]);
        }
        return stringBuilder.toString();
    }
}
