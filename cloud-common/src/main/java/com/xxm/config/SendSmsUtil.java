package com.xxm.config;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xxm.error.ServiceException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;

@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = "sms")
public class SendSmsUtil {

    private String url; //APP接入地址(在控制台"应用管理"页面获取)+接口访问URI
    private String appKey; //APP_Key
    private String appSecret; //APP_Secret
    private String sender; //国内短信签名通道号或国际/港澳台短信通道号
    private String templateId; //模板ID

    //条件必填,国内短信关注,当templateId指定的模板类型为通用模板时生效且必填,必须是已审核通过的,与模板类型一致的签名名称
    //国际/港澳台短信不用关注该参数
    private String signature; //签名名称

    //无需修改,用于格式化鉴权头域,给"X-WSSE"参数赋值
    private static final String WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";
    //无需修改,用于格式化鉴权头域,给"Authorization"参数赋值
    private static final String AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";

    /**
     * 模板变量，此处以单变量验证码短信为例，请客户自行生成6位验证码
     * 选填,使用无变量模板时请赋空值 String templateParas = "";
     * 单变量模板示例:模板内容为"您的验证码是${1}"时,templateParas可填写为"[\"369751\"]"
     * 双变量模板示例:模板内容为"您有${1}件快递请到${2}领取"时,templateParas可填写为"[\"3\",\"人民公园正门\"]"
     * 模板中的每个变量都必须赋值，且取值不能为空
     * 查看更多模板和变量规范:产品介绍>模板和变量规范
     *
     * @param templateParas "[\"369751\"]"
     * @param receiver      短信接收人号码 必填,全局号码格式(包含国家码),示例:+8615123456789,多个号码之间用英文逗号分隔
     */
    public void sendSms(String receiver, String templateParas) {
        //选填,短信状态报告接收地址,推荐使用域名,为空或者不填表示不接收状态报告
        String statusCallBack = "";
        templateParas = "[\"" + templateParas + "\"]";
        //请求Body,不携带签名名称时,signature请填null
        String body = buildRequestBody(sender, receiver, templateId, templateParas, statusCallBack, signature);
        if (null == body || body.isEmpty()) {
            log.info("body is null.");
            return;
        }

        //请求Headers中的X-WSSE参数值
        String wsseHeader = buildWsseHeader(appKey, appSecret);
        if (null == wsseHeader || wsseHeader.isEmpty()) {
            log.info("wsse header is null.");
            return;
        }
        HostnameVerifier hv = (hostname, session) -> true;
        try {
            trustAllHttpsCertificates();
        } catch (Exception e) {
            log.error("发送短信 信任所有Https证书异常");
            throw new RuntimeException(e);
        }

        HttpResponse httpResponse = HttpRequest.post(url)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", AUTH_HEADER_VALUE)
                .header("X-WSSE", wsseHeader)
                .setHostnameVerifier(hv)
                .body(body).execute();
        String response = httpResponse.body();
        JSONObject jsonObject = JSON.parseObject(response);
        Object code = jsonObject.get("code");
        if (code == null) {
            log.info(response);
            throw new ServiceException("短信发送异常");
        }
        if (!"000000".equals(code)) {
            log.info(response);
            throw new ServiceException("短信发送失败");
        }
    }

    /**
     * 构造请求Body体
     *
     * @param signature | 签名名称,使用国内短信通用模板时填写
     */
    private static String buildRequestBody(String sender, String receiver, String templateId, String templateParas,
                                           String statusCallBack, String signature) {
        if (null == sender || null == receiver || null == templateId || sender.isEmpty() || receiver.isEmpty() || templateId.isEmpty()) {
            log.info("buildRequestBody(): sender, receiver or templateId is null.");
            return null;
        }
        Map<String, String> map = new HashMap<>();

        map.put("from", sender);
        map.put("to", receiver);
        map.put("templateId", templateId);
        if (null != templateParas && !templateParas.isEmpty()) {
            map.put("templateParas", templateParas);
        }
        if (null != statusCallBack && !statusCallBack.isEmpty()) {
            map.put("statusCallback", statusCallBack);
        }
        if (null != signature && !signature.isEmpty()) {
            map.put("signature", signature);
        }

        StringBuilder sb = new StringBuilder();
        String temp = "";

        for (String s : map.keySet()) {
            try {
                temp = URLEncoder.encode(map.get(s), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            sb.append(s).append("=").append(temp).append("&");
        }

        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    /**
     * 构造X-WSSE参数值
     */
    private static String buildWsseHeader(String appKey, String appSecret) {
        if (null == appKey || null == appSecret || appKey.isEmpty() || appSecret.isEmpty()) {
            log.info("buildWsseHeader(): appKey or appSecret is null.");
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String time = sdf.format(new Date()); //Created
        String nonce = UUID.randomUUID().toString().replace("-", ""); //Nonce

        MessageDigest md;
        byte[] passwordDigest = null;

        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update((nonce + time + appSecret).getBytes());
            passwordDigest = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String passwordDigestBase64Str = Base64.getEncoder().encodeToString(passwordDigest); //PasswordDigest
        return String.format(WSSE_HEADER_FORMAT, appKey, passwordDigestBase64Str, nonce, time);
    }

    private static void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
        };
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }
}
