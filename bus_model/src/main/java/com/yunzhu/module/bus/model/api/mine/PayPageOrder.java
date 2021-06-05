package com.yunzhu.module.bus.model.api.mine;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author: lisc
 * @date: 2020-04-22 上午 11:53
 * @desc:
 */
public class PayPageOrder implements Parcelable {

    public String orderNo; //支付订单号
    public String amount; //支付金额
    public String merchantName;//商户名称
    public String productName; //商品名称
    public String payLimit; //限定支付方式
    public String notifyTag; //支付事件通知的Tag

    public PayPageOrder(){ }

    protected PayPageOrder(Parcel in) {
        orderNo = in.readString();
        amount = in.readString();
        merchantName = in.readString();
        productName = in.readString();
        payLimit = in.readString();
        notifyTag = in.readString();
    }

    public static final Creator<PayPageOrder> CREATOR = new Creator<PayPageOrder>() {
        @Override
        public PayPageOrder createFromParcel(Parcel in) {
            return new PayPageOrder(in);
        }

        @Override
        public PayPageOrder[] newArray(int size) {
            return new PayPageOrder[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderNo);
        dest.writeString(amount);
        dest.writeString(merchantName);
        dest.writeString(productName);
        dest.writeString(payLimit);
        dest.writeString(notifyTag);
    }
}
