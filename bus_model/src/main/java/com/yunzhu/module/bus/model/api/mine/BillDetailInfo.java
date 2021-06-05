package com.yunzhu.module.bus.model.api.mine;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author:lisc
 * @date:2019-08-08
 */
public class BillDetailInfo implements Parcelable{

    public String tradeOrderNo;
    public int tradeType;
    public Long tradeTime;
    public String tradeName;
    public float tradeAmount;
    public String tradePayNo;
    public String remark;
    public String payWay;
    public boolean debit;
    public int orderStatus;
    public String orderStatusName;
    public String custName;
    public String goodsName;
    public String custOrderNo;

    public BillDetailInfo()
    {}

    protected BillDetailInfo(Parcel in) {
        tradeOrderNo = in.readString();
        tradeType = in.readInt();
        tradeTime = in.readLong();
        tradeName = in.readString();
        tradeAmount = in.readFloat();
        tradePayNo = in.readString();
        remark = in.readString();
        payWay = in.readString();
        debit = in.readByte() != 0;
        orderStatus = in.readInt();
        orderStatusName = in.readString();
        custName = in.readString();
        goodsName = in.readString();
        custOrderNo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tradeOrderNo);
        dest.writeInt(tradeType);
        dest.writeLong(tradeTime);
        dest.writeString(tradeName);
        dest.writeFloat(tradeAmount);
        dest.writeString(tradePayNo);
        dest.writeString(remark);
        dest.writeString(payWay);
        dest.writeByte((byte) (debit ? 1 : 0));
        dest.writeInt(orderStatus);
        dest.writeString(orderStatusName);
        dest.writeString(custName);
        dest.writeString(goodsName);
        dest.writeString(custOrderNo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BillDetailInfo> CREATOR = new Creator<BillDetailInfo>() {
        @Override
        public BillDetailInfo createFromParcel(Parcel in) {
            return new BillDetailInfo(in);
        }

        @Override
        public BillDetailInfo[] newArray(int size) {
            return new BillDetailInfo[size];
        }
    };
}
