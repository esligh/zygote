package com.yunzhu.module.bus.model.api.mine;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author:lisc
 * @date:2019-06-12
 */

public class BankCard implements Parcelable{

   public String bankCardNo;
   public String bankType;
    public String bankName;
   public int bankCardType;
   public String token;
   public String imgUrl;
   public String colorCode;
   public String dailyLimit;
   public String singleLimit;
   public String bankCardCode;

   public BankCard(){}

    protected BankCard(Parcel in) {
        bankCardNo = in.readString();
        bankType = in.readString();
        bankName = in.readString();
        bankCardType = in.readInt();
        token = in.readString();
        imgUrl = in.readString();
        colorCode = in.readString();
        dailyLimit = in.readString();
        singleLimit = in.readString();
        bankCardCode = in.readString();
    }

    public static final Creator<BankCard> CREATOR = new Creator<BankCard>() {
        @Override
        public BankCard createFromParcel(Parcel in) {
            return new BankCard(in);
        }

        @Override
        public BankCard[] newArray(int size) {
            return new BankCard[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bankCardNo);
        parcel.writeString(bankType);
        parcel.writeString(bankName);
        parcel.writeInt(bankCardType);
        parcel.writeString(token);
        parcel.writeString(imgUrl);
        parcel.writeString(colorCode);
        parcel.writeString(dailyLimit);
        parcel.writeString(singleLimit);
        parcel.writeString(bankCardCode);
    }
}
