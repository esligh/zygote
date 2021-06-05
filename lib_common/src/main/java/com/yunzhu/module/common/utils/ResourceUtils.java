package com.yunzhu.module.common.utils;

import android.content.Context;
import android.util.TypedValue;

import com.yunzhu.module.common.base.BaseApplication;

public class ResourceUtils {

    private static TypedValue mTmpValue = new TypedValue();

    private ResourceUtils(){}

    public static float getXmlDef(int id)
    {
        return getXmlDef(BaseApplication.Companion.getInstance(),id);
    }

    public static float getXmlDef(Context context, int id) {
        synchronized (mTmpValue) {
            TypedValue value = mTmpValue;
            context.getResources().getValue(id, value, true);
            return TypedValue.complexToFloat(value.data);
        }
    }

    public static float getDimen(int id)
    {
        return BaseApplication.Companion.getInstance().getResources().getDimension(id);
    }

    public static int getColor(int id)
    {
        return BaseApplication.Companion.getInstance().getResources().getColor(id);
    }

    public static String getStr(int id)
    {
        return BaseApplication.Companion.getInstance().getResources().getString(id);
    }
}