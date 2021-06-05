package com.yunzhu.module.common.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author:lisc
 * @date:2019-06-13
 */

public final class UIHelper {
    private static final String[] IDCARD = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "x", "X", };

    private UIHelper(){}

    public static void setIdEditText(EditText et)
    {
        final List<String> idCardList = Arrays.asList(IDCARD);
        InputFilter inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = 0; i < source.toString().length(); i++) {
                    if (!idCardList.contains(String.valueOf(source.charAt(i)))) {
                        return "";
                    }
                    if (et.getText().toString().length() < 17) {
                        if ("x".equals(String.valueOf(source.charAt(i))) || "X".equals(String.valueOf(source.charAt(i)))) {
                            return "";
                        }
                    }
                }
                return null;
            }
        };
        et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18), inputFilter});
    }


    public static void disableSoftInputMethod(EditText et)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            et.setShowSoftInputOnFocus(false);
        }else{
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(et, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void hideSoftInputMethod(Activity activity) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && activity.getCurrentFocus() != null) {
            if (activity.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
