package com.xxm.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static String md5(String source) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] result = md.digest(source.getBytes());
        StringBuilder buf = new StringBuilder();
        for (byte b : result) {
            buf.append(String.format("%02X", b));
        }
        return buf.toString().toLowerCase();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(md5("123456"));
    }

}
