package com.yunzhu.module.bus.model.api.mine;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author: lisc
 * @date: 2020-05-29 上午 10:57
 * @desc: 风控类型描述
 */
public class RiskCtrlType implements Parcelable {
    public String riskType;
    public String riskSeq;
    public String confirmTips;
    public int riskIndex;
    public RiskCtrlType(){}

    public RiskCtrlType(String type,String req,String confirmTips,int index)
    {
        this.riskType = type;
        this.riskSeq = req;
        this.confirmTips = confirmTips;
        this.riskIndex = index;
    }

    protected RiskCtrlType(Parcel in) {
        riskType = in.readString();
        riskSeq = in.readString();
        confirmTips = in.readString();
        riskIndex = in.readInt();
    }

    public static final Creator<RiskCtrlType> CREATOR = new Creator<RiskCtrlType>() {
        @Override
        public RiskCtrlType createFromParcel(Parcel in) {
            return new RiskCtrlType(in);
        }

        @Override
        public RiskCtrlType[] newArray(int size) {
            return new RiskCtrlType[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(riskType);
        dest.writeString(riskSeq);
        dest.writeString(confirmTips);
        dest.writeInt(riskIndex);
    }
}
