package com.yunzhu.module.common.utils;

import android.text.TextUtils;
import android.util.Log;

import com.yunzhu.module.common.BuildConfig;
import com.yunzhu.module.common.config.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Administrator on 2019-05-08.
 */

public final class L {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final int JSON_INDENT = 4;
    private static final int LINE_LIMIT_SIZE = 3*1024;

    private static final int V = 0x1;
    private static final int D = 0x2;
    private static final int I = 0x3;
    private static final int W = 0x4;
    private static final int E = 0x5;
    private static final int A = 0x6;
    private static final int JSON = 0x7;

    private static String Tag = "pay";
    private static boolean isDebug = BuildConfig.BUILD_TYPE.equalsIgnoreCase(Config.BuildType.DEBUG) ||
            BuildConfig.BUILD_TYPE.equalsIgnoreCase(Config.BuildType.TEST);
    private L(){}

    public static void setTag(String tag)
    {
        Tag = tag;
    }

    public static void setLogEnable(boolean enable){
        isDebug = enable;
    }

    public static void d(String s)
    {
        if(isDebug){
            logdInner(Tag,s);
        }
    }

    public static void i(String s)
    {
        if(isDebug){
            logiInner(Tag,s);
        }
    }

    public static void e(String s)
    {
        if(isDebug){
            logeInner(Tag,s);
        }
    }

    public static void d(String tag, String s)
    {
        if(isDebug){
            logdInner(tag,s);
        }
    }

    public static void i(String tag, String s)
    {
        if(isDebug){
            logiInner(tag,s);
        }
    }

    public static void e(String tag, String s)
    {
        if(isDebug){
            logeInner(tag,s);
        }
    }

    public static void json(String msg)
    {
        if(isDebug){
            json(Tag,msg);
        }
    }

    public static void json(String tag, String desc, String msg)
    {
        if(isDebug){
            L.i(tag,desc);
            json(tag,msg);
//            Logger.json(msg);
        }
    }

    /**
     * 截断输出日志
     * @param msg
     */
    public static void logdInner(String tag, String msg) {
        if(TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg))
            return;
        int segmentSize = LINE_LIMIT_SIZE;
        long length = msg.length();
        if (length <= segmentSize ) {// 长度小于等于限制直接打印
            Log.d(tag, msg);
        }else {
            while (msg.length() > segmentSize ) {// 循环分段打印日志
                String logContent = msg.substring(0, segmentSize );
                msg = msg.replace(logContent, "");
                Log.d(tag,logContent);
            }
            Log.d(tag,msg);// 打印剩余日志
        }
    }

    /**
     * 截断输出日志
     * @param msg
     */
    public static void logiInner(String tag, String msg) {
        if(TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg))
            return;
        int segmentSize = LINE_LIMIT_SIZE;
        long length = msg.length();
        if (length <= segmentSize ) {// 长度小于等于限制直接打印
            Log.i(tag, msg);
        }else {
            while (msg.length() > segmentSize ) {// 循环分段打印日志
                String logContent = msg.substring(0, segmentSize );
                msg = msg.replace(logContent, "");
                Log.i(tag,logContent);
            }
            Log.i(tag,msg);// 打印剩余日志
        }
    }

    /**
     * 截断输出日志
     * @param msg
     */
    public static void logeInner(String tag, String msg) {
        if(TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg))
            return;
        int segmentSize = LINE_LIMIT_SIZE;
        long length = msg.length();
        if (length <= segmentSize ) {// 长度小于等于限制直接打印
            Log.e(tag, msg);
        }else {
            while (msg.length() > segmentSize ) {// 循环分段打印日志
                String logContent = msg.substring(0, segmentSize );
                msg = msg.replace(logContent, "");
                Log.e(tag,logContent);
            }
            Log.e(tag,msg);// 打印剩余日志
        }
    }

    public static void json(String tag, String jsonStr) {
        printLog(JSON, tag, jsonStr);
    }

    private static void printLog(int type, String tagStr, Object objectMsg) {
        String msg;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        int index = 4;
        String className = stackTrace[index].getFileName();
        String methodName = stackTrace[index].getMethodName();
        int lineNumber = stackTrace[index].getLineNumber();

        String tag = (tagStr == null ? className : tagStr);
        methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ (").append(className).append(":").append(lineNumber).append(")#").append(methodName).append(" ] ");

        if (objectMsg == null) {
            msg = "Log with null Object";
        } else {
            msg = objectMsg.toString();
        }
        if (msg != null && type != JSON) {
            stringBuilder.append(msg);
        }

        String logStr = stringBuilder.toString();

        switch (type) {
            case V:
                Log.v(tag, logStr);
                break;
            case D:
                Log.d(tag, logStr);
                break;
            case I:
                Log.i(tag, logStr);
                break;
            case W:
                Log.w(tag, logStr);
                break;
            case E:
                Log.e(tag, logStr);
                break;
            case A:
                Log.wtf(tag, logStr);
                break;
            case JSON: {

                if (TextUtils.isEmpty(msg)) {
                    Log.d(tag, "Empty or Null json content");
                    return;
                }

                String message = null;

                try {
                    if (msg.startsWith("{")) {
                        JSONObject jsonObject = new JSONObject(msg);
                        message = jsonObject.toString(JSON_INDENT);
                    } else if (msg.startsWith("[")) {
                        JSONArray jsonArray = new JSONArray(msg);
                        message = jsonArray.toString(JSON_INDENT);
                    }
                } catch (JSONException e) {
                    e(tag, e.getCause().getMessage() + "\n" + msg);
                    return;
                }

                printLine(tag, true);
                message = logStr + LINE_SEPARATOR + message;
                String[] lines = message.split(LINE_SEPARATOR);
                StringBuilder jsonContent = new StringBuilder();
                for (String line : lines) {
                    jsonContent.append("║ ").append(line).append(LINE_SEPARATOR);
                    logdInner(tag, jsonContent.toString());
                    jsonContent.delete(0,jsonContent.length());
                }
//                if (jsonContent.toString().length() > 3200) {
//                    Log.w(tag, "jsonContent.length = " + jsonContent.toString().length());
//                    int chunkCount = jsonContent.toString().length() / 3200;
//                    for (int i = 0; i <= chunkCount; i++) {
//                        int max = 3200 * (i + 1);
//                        if (max >= jsonContent.toString().length()) {
//                            Log.w(tag, jsonContent.toString().substring(3200 * i));
//                        } else {
//                            Log.w(tag, jsonContent.toString().substring(3200 * i, max));
//                        }
//                    }
//                } else {
//                    Log.w(tag, jsonContent.toString());
//                }
                printLine(tag, false);
            }
            break;
        }

    }

    private static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.w(tag, "########################################################\n");
        } else {
            Log.w(tag, "########################################################\n");
        }
    }

}
