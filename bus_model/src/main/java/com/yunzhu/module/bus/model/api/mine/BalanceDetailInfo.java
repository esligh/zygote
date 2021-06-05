package com.yunzhu.module.bus.model.api.mine;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author:lisc
 * @date:2019-08-08
 */
public class BalanceDetailInfo implements Parcelable{

    public String tradeName;
    public float amount;
    public String orderNo;
    public boolean debit;
    public String tradeTime;
    public float balance;

    public BalanceDetailInfo()
    {}

    protected BalanceDetailInfo(Parcel in) {
        tradeName = in.readString();
        amount = in.readFloat();
        orderNo = in.readString();
        debit = in.readByte() != 0;
        tradeTime = in.readString();
        balance = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tradeName);
        dest.writeFloat(amount);
        dest.writeString(orderNo);
        dest.writeByte((byte) (debit ? 1 : 0));
        dest.writeString(tradeTime);
        dest.writeFloat(balance);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BalanceDetailInfo> CREATOR = new Creator<BalanceDetailInfo>() {
        @Override
        public BalanceDetailInfo createFromParcel(Parcel in) {
            return new BalanceDetailInfo(in);
        }

        @Override
        public BalanceDetailInfo[] newArray(int size) {
            return new BalanceDetailInfo[size];
        }
    };
}
