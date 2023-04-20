package com.xxm.utils;

import cn.felord.payment.wechat.WechatPayProperties;
import cn.felord.payment.wechat.v3.WechatApiProvider;
import cn.felord.payment.wechat.v3.WechatDirectPayApi;
import cn.felord.payment.wechat.v3.model.Amount;
import cn.felord.payment.wechat.v3.model.PayParams;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.xxm.consts.BaseConst;
import com.xxm.pay.wechat.PlaceOrderReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
@RequiredArgsConstructor
public class WechatPayUtil {

    private final WechatApiProvider wechatApiProvider;

    private static WechatApiProvider provider;

    @PostConstruct
    public void init() {
        provider = this.wechatApiProvider;
    }


    /**
     * 支付下单请求方法
     *
     * @param placeOrderReq placeOrderReq
     * @return cn.felord.payment.wechat.v3.model.PayParams
     */
    public static PayParams payParams(PlaceOrderReq placeOrderReq) {
        Amount amount = new Amount();
        amount.setTotal(placeOrderReq.getTotal());
        PayParams payParams = new PayParams();
        payParams.setAttach(placeOrderReq.getAttach());
        payParams.setDescription(placeOrderReq.getDescription());
        payParams.setOutTradeNo(placeOrderReq.getTradeNo());
        payParams.setAmount(amount);
        payParams.setTimeExpire(DateUtil.format(DateUtil.offsetMinute(DateUtil.date(), 5), DatePattern.UTC_WITH_XXX_OFFSET_PATTERN));
        WechatDirectPayApi wechatDirectPayApi = provider.directPayApi(BaseConst.MERCHANT);
        WechatPayProperties.V3 v3 = wechatDirectPayApi.wechatMetaBean().getV3();
        payParams.setAppid(v3.getAppId());
        payParams.setMchid(v3.getMchId());
        payParams.setNotifyUrl("/merchant/pay/wechatPayCallback");
        return payParams;
    }

}
