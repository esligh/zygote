package com.yunzhu.module.bus.model.api.mine;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author:lisc
 * @date:2019-06-21
 */

public class BankCardBin implements Parcelable{

    public String cardNo;
    public String cardValidDate;
    public String bankName;
    public String bankType;//银行卡行别
    public String bankIdentityNo;
    public String cardName;//卡片名称
    public int bankCardType;//卡片类别
    public String cardTypeName;//卡片类别名称
    public BankCardBin()
    {

    }

    protected BankCardBin(Parcel in) {
        cardNo = in.readString();
        cardValidDate = in.readString();
        bankName = in.readString();
        bankType = in.readString();
        bankIdentityNo = in.readString();
        cardName = in.readString();
        bankCardType = in.readInt();
        cardTypeName = in.readString();
    }

    public static final Creator<BankCardBin> CREATOR = new Creator<BankCardBin>() {
        @Override
        public BankCardBin createFromParcel(Parcel in) {
            return new BankCardBin(in);
        }

        @Override
        public BankCardBin[] newArray(int size) {
            return new BankCardBin[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cardNo);
        parcel.writeString(cardValidDate);
        parcel.writeString(bankName);
        parcel.writeString(bankType);
        parcel.writeString(bankIdentityNo);
        parcel.writeString(cardName);
        parcel.writeInt(bankCardType);
        parcel.writeString(cardTypeName);
    }
}
