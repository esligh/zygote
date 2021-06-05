package com.yunzhu.module.common.interfaces

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText

/**
 * @author:lisc
 * @date:2019-06-14
 */
abstract class BankCardTextWatcher(private val mEditText: EditText) : TextWatcher {

    private var shouldStopChange = false
    private val space = " "

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

    override fun afterTextChanged(editable: Editable) {
        format(editable)
    }

    private fun format(editable: Editable) {
        if (shouldStopChange) {
            shouldStopChange = false
            return
        }
        shouldStopChange = true
        val str =
            editable.toString().trim { it <= ' ' }.replace(space.toRegex(), "")
        val len = str.length
        val courPos: Int
        val builder = StringBuilder()
        for (i in 0 until len) {
            builder.append(str[i])
            if (i == 3 || i == 7 || i == 11 || i == 15) {
                if (i != len - 1) builder.append(space)
            }
        }
        courPos = builder.length
        mEditText.setText(builder.toString())
        mEditText.setSelection(courPos)
    }

    private val bankCardText: String
        get() = mEditText.text.toString().trim { it <= ' ' }.replace(" ".toRegex(), "")

    val isBankCard: Boolean get() = checkBankCard(bankCardText)

    /**
     * 校验银行卡卡号
     * @param cardId
     * @return
     */
    private fun checkBankCard(cardId: String): Boolean {
        if (TextUtils.isEmpty(cardId)) {
            return false
        }
        val bit = getBankCardCheckCode(cardId.substring(0, cardId.length - 1))
        return if (bit == 'N') {
            false
        } else cardId[cardId.length - 1] == bit
    }

    /**
     * @param nonCheckCodeCardId
     * @return
     */
    private fun getBankCardCheckCode(nonCheckCodeCardId: String): Char {
        if (TextUtils.isEmpty(nonCheckCodeCardId)
            || !nonCheckCodeCardId.matches(Regex("\\d+"))
            || nonCheckCodeCardId.length < 16 || nonCheckCodeCardId.length > 19
        ) {
            //如果传的不是数据返回N
            return 'N'
        }
        val chs = nonCheckCodeCardId.trim { it <= ' ' }.toCharArray()
        var sum = 0
        var i = chs.size - 1
        var j = 0
        while (i >= 0) {
            var k = chs[i] - '0'
            if (j % 2 == 0) {
                k *= 2
                k = k / 10 + k % 10
            }
            sum += k
            i--
            j++
        }
        return if (sum % 10 == 0) '0' else (10 - sum % 10 + '0'.toInt()).toChar()
    }

}