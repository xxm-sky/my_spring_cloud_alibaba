package com.xxm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;


public enum ERegion {
    PROVINCE("province", "省"),
    CITY("city", "市"),
    COUNTY("county", "县");

    @EnumValue
    String value;
    String desc;

    ERegion(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
