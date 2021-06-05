package com.yunzhu.module.common.vm;

import androidx.annotation.StringRes;

/**
 * @author:lisc
 * @date:2019-10-18
 */

public interface IBaseView {
    void showLoading(@StringRes int msgRes);
    void closeLoading();
    void setLoadingMessage(String message);
    void closePage();
}
