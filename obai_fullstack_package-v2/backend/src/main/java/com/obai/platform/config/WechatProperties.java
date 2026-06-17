package com.obai.platform.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * WeChat Mini Program and WeChat Pay V3 configuration.
 *
 * <p>Development can run with devFallbackEnabled=true. Production must set real AppID,
 * secret, merchant id, certificate serial number and merchant private key through
 * environment variables.</p>
 */
@Component
@ConfigurationProperties(prefix = "obai.wechat")
public class WechatProperties {
    private String appid;
    private String secret;
    private Boolean devFallbackEnabled = true;
    private String payMchId;
    private String paySerialNo;
    private String payPrivateKey;
    private String payNotifyUrl;
    private String payDescription = "OBAI 菌淘商城订单";
    private String payApiHost = "https://api.mch.weixin.qq.com";

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Boolean getDevFallbackEnabled() {
        return devFallbackEnabled;
    }

    public void setDevFallbackEnabled(Boolean devFallbackEnabled) {
        this.devFallbackEnabled = devFallbackEnabled;
    }

    public String getPayMchId() {
        return payMchId;
    }

    public void setPayMchId(String payMchId) {
        this.payMchId = payMchId;
    }

    public String getPaySerialNo() {
        return paySerialNo;
    }

    public void setPaySerialNo(String paySerialNo) {
        this.paySerialNo = paySerialNo;
    }

    public String getPayPrivateKey() {
        return payPrivateKey;
    }

    public void setPayPrivateKey(String payPrivateKey) {
        this.payPrivateKey = payPrivateKey;
    }

    public String getPayNotifyUrl() {
        return payNotifyUrl;
    }

    public void setPayNotifyUrl(String payNotifyUrl) {
        this.payNotifyUrl = payNotifyUrl;
    }

    public String getPayDescription() {
        return payDescription;
    }

    public void setPayDescription(String payDescription) {
        this.payDescription = payDescription;
    }

    public String getPayApiHost() {
        return payApiHost;
    }

    public void setPayApiHost(String payApiHost) {
        this.payApiHost = payApiHost;
    }
}
