package com.yunzhu.module.bus.common.global;
import android.text.TextUtils;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.google.gson.Gson;
import com.yunzhu.module.bus.common.security.KFTSafeStorage;
import com.yunzhu.module.bus.constant.PrefKey;
import com.yunzhu.module.bus.model.api.common.Session;
import com.yunzhu.module.common.http.ApiFactory;
import com.yunzhu.module.config.AppConfig;

/**
 * @author:lisc
 * @date: 2019-06-21
 * @description: Session对象
 */
public enum SessionMgr {

    INSTANCE;
    private static Session sSession;

    public static void setSession(Session response)
    {
        if(response != null){
            sSession = response;
            KFTSafeStorage.INSTANCE.putString(PrefKey.SESSION, new Gson().toJson(response));
        }
    }

    public static boolean isLogin()
    {
        return getSession() != null;
    }

    public static Session getSession()
    {
        if (sSession == null) {
            String str = KFTSafeStorage.INSTANCE.getString(PrefKey.SESSION);
            if (str != null) {
                sSession = new Gson().fromJson(str, Session.class);
            }
        }
        return sSession;
    }

    public static void updateSession(Session session)
    {
        if(sSession != null && session != null){
            sSession.phone = session.phone;
            sSession.profilePicUrl = session.profilePicUrl;
            sSession.backgroundImageUrl = session.backgroundImageUrl;
            sSession.email = session.email;
            sSession.nickName = session.nickName;
            sSession.name = session.name;
            sSession.nationality = session.nationality;
            sSession.gender = session.gender;
            sSession.city = session.city;
            sSession.certNo = session.certNo;
            sSession.personSign = session.personSign;
            sSession.realNameFlag = session.realNameFlag;
            sSession.setTradePasswordFlag = session.setTradePasswordFlag;
            sSession.setLoginPasswordFlag = session.setLoginPasswordFlag;
            sSession.certType = session.certType;
            sSession.uploadIdCardFlag = session.uploadIdCardFlag;
            sSession.certValidDateFlag = session.certValidDateFlag;
            sSession.certValidDate = session.certValidDate;
            sSession.accountLevel = session.accountLevel;

            if(!TextUtils.isEmpty(session.token)){
                sSession.token = session.token;
            }

            KFTSafeStorage.INSTANCE.putString(PrefKey.SESSION, new Gson().toJson(sSession));
        }
    }

    public static void clearSession()
    {
        ClearableCookieJar cookieJar = ApiFactory.Companion.instance().getCookieJar();
        if(cookieJar != null){
            cookieJar.clear();
        }
        sSession = null;
        KFTSafeStorage.INSTANCE.removeKey(PrefKey.SESSION);
        KFTSafeStorage.INSTANCE.putString(PrefKey.SESSION, "");
    }

    public static boolean hasPayPwd()
    {
        Session session = getSession();
        if (session != null) {
            return session.setTradePasswordFlag;
        }
        return false;
    }

    public static boolean hasLoginPwd()
    {
        Session session = getSession();
        if (session != null) {
            return session.setLoginPasswordFlag;
        }
        return false;
    }

    public static boolean hasRealName()
    {
        Session session = getSession();
        if (session != null) {
            return session.realNameFlag;
        }
        return false;
    }

    /**
     * 是否上传了身份证
     * */
    public static boolean hasUploadIdCard()
    {
        Session session = getSession();
        if (session != null) {
            return session.uploadIdCardFlag;
        }
        return false;
    }

    /**
     * 身份证是否过期
     * */
    public static boolean isIdCardValid()
    {
        Session session = getSession();
        if (session != null) {
            return session.certValidDateFlag;
        }
        return false;
    }


}
