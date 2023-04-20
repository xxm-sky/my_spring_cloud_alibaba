package com.xxm.pay.wechat;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderDto {

    @ApiModelProperty("订单ID")
    private String orderId;

    @ApiModelProperty("微信支付二维码")
    private String codeUrl;

    public static PlaceOrderDto generate(ObjectNode node, String orderId) {
        return new PlaceOrderDto(orderId, node.get("code_url").asText());
    }
}
