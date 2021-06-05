package com.yunzhu.module.common.interfaces

import android.text.Editable
import android.text.TextWatcher

/**
 * @author:lisc
 * @date:2019-06-12
 */
abstract class TextWatcherAdapter : TextWatcher {
    override fun beforeTextChanged(
        charSequence: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {}

    override fun onTextChanged(
        charSequence: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {}

    override fun afterTextChanged(editable: Editable) {}
}