package com.xxm.pay.wechat;

import lombok.Data;


@Data
public class WechatResource {

    private String originalType;

    private String algorithm;

    private String ciphertext;

    private String associated_data;

    private String nonce;

}
