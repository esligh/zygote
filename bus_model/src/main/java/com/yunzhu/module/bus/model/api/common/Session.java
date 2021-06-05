package com.yunzhu.module.bus.model.api.common;


import com.yunzhu.module.bus.model.api.mine.RiskCtrlType;

import java.util.ArrayList;

/**
 * @author: lisc
 * @date: 2019-12-20 下午 02:24
 * @desc:
 */
public class Session {
    public String token;
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
    public boolean realNameFlag;
    public boolean setTradePasswordFlag;
    public boolean setLoginPasswordFlag;
    public Boolean certValidDateFlag;
    public String certType;
    public boolean uploadIdCardFlag;
    public String certValidDate;
    public String accountLevel;
    public String payAccountId;
    public boolean hasRisk;
    public ArrayList<RiskCtrlType> riskSteps;
}
