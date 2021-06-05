package com.yunzhu.module.common.entity;

import org.json.JSONObject;

/**
 * @author: lisc
 * @date: 2020-03-31 上午 10:56
 * @desc:
 */
public class ResponseWithJson {
    public int status; // 返回的code
    public String errorCode;
    public String errorMsg; // message 可用来返回接口的说明
    public JSONObject data;// 具体的数据结果
}
