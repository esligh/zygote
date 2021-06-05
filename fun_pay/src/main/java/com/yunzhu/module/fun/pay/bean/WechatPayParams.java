package com.yunzhu.module.fun.pay.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author: lisc
 * @date: 2020-09-09 上午 11:30
 * @desc:
 */
public class WechatPayParams {
    @SerializedName("appid")
    public String appId;
    @SerializedName("partnerid")
    public String partnerId;
    @SerializedName("prepayid")
    public String prepayId;
    @SerializedName("noncestr")
    public String nonceStr;
    @SerializedName("sign")
    public String paySign;
    @SerializedName("timestamp")
    public String timeStamp;
    @SerializedName("package")
    public String pkg;
}
