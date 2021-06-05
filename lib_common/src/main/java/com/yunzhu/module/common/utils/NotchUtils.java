package com.yunzhu.module.common.utils;

import android.content.Context;
import android.os.Build;
import android.view.Window;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Method;

/**
 * @author: lisc
 * @date: 2020-04-26 下午 03:52
 * @desc:
 */
public class NotchUtils {

    public NotchUtils(){
        throw new UnsupportedOperationException("");
    }

    public static boolean hasNotchInOppo(Context context){
        return context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }


    public static final int NOTCH_IN_SCREEN_VOIO=0x00000020;//是否有凹槽
    public static final int ROUNDED_IN_SCREEN_VOIO=0x00000008;//是否有圆角

    public static boolean hasNotchInScreenAtVoio(Context context){
        boolean ret = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class FtFeature = cl.loadClass("com.util.FtFeature");
            Method get = FtFeature.getMethod("isFeatureSupport",int.class);
            ret = (boolean) get.invoke(FtFeature,NOTCH_IN_SCREEN_VOIO);
        } catch (ClassNotFoundException e) { }
        catch (NoSuchMethodException e) { }
        catch (Exception e) { }
        finally {
            return ret;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean hasNotchInScreenAtHuaWei(Window window) {
        boolean isNotchScreen = false;
        try {
            ClassLoader cl = window.getContext().getClassLoader();
            Class HwNotchSizeUtil =   cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            isNotchScreen = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {

        } catch (NoSuchMethodException e) {

        } catch (Exception e) {

        } finally {
            return isNotchScreen;
        }
    }

}
