package com.xxm.pay.wechat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信native下单入参
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderReq {

    /**
     * 商品描述
     */
    private String description;

    /**
     * 订单ID 唯一
     */
    private String tradeNo;

    /**
     * 自定义传参,支付成功通知原样返回
     */
    private String attach;

    /**
     * 金额(单位分)
     */
    private Integer total;

    /**
     * 业务id 业务类型
     */
    private Long businessId;
    private String businessType;
}
