package com.xxm.pay.wechat;

import lombok.Data;



@Data
public class WechatCallBackDto {

    private String id;

    private String createTime;

    private String resourceType;

    private String eventType;

    private String summary;

    private WechatResource resource;

}
