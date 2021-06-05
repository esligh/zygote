package com.yunzhu.module.common.interfaces

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * @author:lisc
 * @date:2019-06-14
 */
abstract class PhoneNumberTextWatcher(private val mEditText: EditText) : TextWatcher {

    override fun beforeTextChanged(
        charSequence: CharSequence,
        i: Int,
        i1: Int,
        i2: Int
    ) {}

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        if (s == null || s.isEmpty()) {
            return
        }
        val sb = StringBuilder()
        for (i in s.indices) {
            if (i != PHONE_INDEX_3 && i != PHONE_INDEX_8 && s[i] == ' ') {
                continue
            } else {
                sb.append(s[i])
                if ((sb.length == PHONE_INDEX_4 || sb.length == PHONE_INDEX_9) && sb[sb.length - 1] != ' '
                ) {
                    sb.insert(sb.length - 1, ' ')
                }
            }
        }
        if (sb.toString() != s.toString()) {
            var index = start + 1
            if (sb[start] == ' ') {
                if (before == 0) {
                    index++
                } else {
                    index--
                }
            } else {
                if (before == 1) {
                    index--
                }
            }
            mEditText.setText(sb.toString())
            mEditText.setSelection(index)
        }
    }

    override fun afterTextChanged(editable: Editable) {}

    companion object {
        private const val PHONE_INDEX_3 = 3
        private const val PHONE_INDEX_4 = 4
        private const val PHONE_INDEX_8 = 8
        private const val PHONE_INDEX_9 = 9
    }

}