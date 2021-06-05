package com.yunzhu.module.bus.model.api.mine;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author:lisc
 * @date:2019-07-17
 */
public class CustomerInfo implements Parcelable{

    public String accountNo;
    public String phone;
    public String profilePicUrl;
    public String backgroundImageUrl;
    public String email;
    public String nickName;
    public String name;
    public String nationality;
    public String gender;
    public String city;
    public String certNo;
    public String personSign;
    public boolean realNameFlag; //实名标识
    public boolean setTradePasswordFlag; //是否设置交易密码
    public boolean setLoginPasswordFlag; //是否设置登陆密码
    public String certType;
    public String certValidDate; //证件有效期
    public boolean uploadIdCardFlag; //上传证件标识
    public String accountLevel;
    public Boolean certValidDateFlag;//证件是否过有效期


    protected CustomerInfo(Parcel in) {
        accountNo = in.readString();
        phone = in.readString();
        profilePicUrl = in.readString();
        backgroundImageUrl = in.readString();
        email = in.readString();
        nickName = in.readString();
        name = in.readString();
        nationality = in.readString();
        gender = in.readString();
        city = in.readString();
        certNo = in.readString();
        personSign = in.readString();
        realNameFlag = in.readByte() != 0;
        setTradePasswordFlag = in.readByte() != 0;
        setLoginPasswordFlag = in.readByte() != 0;
        certType = in.readString();
        certValidDate = in.readString();
        uploadIdCardFlag = in.readByte() != 0;
        accountLevel = in.readString();
        certValidDateFlag = in.readByte() != 0;
    }

    public static final Creator<CustomerInfo> CREATOR = new Creator<CustomerInfo>() {
        @Override
        public CustomerInfo createFromParcel(Parcel in) {
            return new CustomerInfo(in);
        }

        @Override
        public CustomerInfo[] newArray(int size) {
            return new CustomerInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(accountNo);
        dest.writeString(phone);
        dest.writeString(profilePicUrl);
        dest.writeString(backgroundImageUrl);
        dest.writeString(email);
        dest.writeString(nickName);
        dest.writeString(name);
        dest.writeString(nationality);
        dest.writeString(gender);
        dest.writeString(city);
        dest.writeString(certNo);
        dest.writeString(personSign);
        dest.writeByte((byte) (realNameFlag ? 1 : 0));
        dest.writeByte((byte) (setTradePasswordFlag ? 1 : 0));
        dest.writeByte((byte) (setLoginPasswordFlag ? 1 : 0));
        dest.writeString(certType);
        dest.writeString(certValidDate);
        dest.writeByte((byte) (uploadIdCardFlag ? 1 : 0));
        dest.writeString(accountLevel);
        dest.writeByte((byte) ((certValidDateFlag!=null && certValidDateFlag) ? 1 : 0));
    }
}
