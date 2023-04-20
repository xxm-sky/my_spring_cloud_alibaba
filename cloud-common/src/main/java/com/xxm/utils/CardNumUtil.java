package com.xxm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CardNumUtil {

    public static String getCardNum(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddmmss");
        String s = UUID.randomUUID().toString().replaceAll("-","");
        return s+""+simpleDateFormat.format(new Date());
    }

    /**
     * 生成随机UUID做邀请码
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
