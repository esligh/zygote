package com.yunzhu.module.bus.common.extensions

import android.app.Activity
import android.app.Dialog
import android.app.DownloadManager
import android.text.SpannableStringBuilder
import android.view.Gravity
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.SpanUtils
import com.yunzhu.module.bus.common.R
import com.yunzhu.module.bus.model.api.common.VersionInfo
import com.yunzhu.module.common.base.BaseApplication.Companion.instance
import com.yunzhu.module.common.update.updater.Updater
import com.yunzhu.module.common.update.updater.UpdaterConfig
import com.yunzhu.module.common.utils.ResourceUtils
import com.yunzhu.module.common.utils.ToastUtils
import com.yunzhu.module.ui.dialog.CustomAlertDialog

/**
 * @author:jiyc
 * @date:2020/5/20
 */
object UpdateApp {
    /**
     * 下载应用弹框提示
     */
    fun dialog(activity: Activity, ver : VersionInfo, tooLow: Boolean = false,
               onConfirm:(dlg: Dialog)->Unit={}, onCancel:(dlg: Dialog)->Unit={}) {
        val negBtnText = if (tooLow) {
            ResourceUtils.getStr(R.string.frame_exit)
        }else {
            ResourceUtils.getStr(R.string.frame_download_in_future)
        }
        val builder = CustomAlertDialog.Builder(activity)
        builder.setTitle(ResourceUtils.getStr(R.string.frame_version_update))
            .setMessage(buildRichText(ver, tooLow))
            .showTitle(true)
            .setContentGravity(Gravity.START)
            .setNegBtn(negBtnText){
                it.dismiss()
                onCancel(it)
            }
            .setPosBtn(ResourceUtils.getStr(R.string.frame_lovely_update)){
                it.dismiss()
                downloadThenInstall(activity, ver)
                onConfirm(it)
            }
        builder.create().show()
    }

    private fun downloadThenInstall(activity: Activity, ver : VersionInfo){
        ToastUtils.showShort(R.string.frame_start_download)
        val config = UpdaterConfig.Builder(activity)
            .setTitle(AppUtils.getAppName())
            .setDescription(ResourceUtils.getStr(R.string.frame_download_desc))
            .setFileUrl(ver.url)
            .setCanMediaScanner(true)
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .build()
        Updater.get().download(config)
    }

    private fun buildRichText(ver : VersionInfo, tooLow : Boolean) : SpannableStringBuilder {
        val textSize = ResourceUtils.getDimen(R.dimen.dp_5).toInt()
        val lineHeightTitle = SizeUtils.dp2px(ResourceUtils.getDimen(R.dimen.dp_15))
        val lineHeightItem = SizeUtils.dp2px(ResourceUtils.getDimen(R.dimen.dp_10))
        val newVersionGeneralInfo = String.format(
            instance.resources.getString(R.string.frame_new_version_info, ver.version))
        val lowVersionRiskTip = ResourceUtils.getStr(R.string.frame_new_version_risk_tip)
        val span = SpanUtils()
        if (tooLow) {
            span.appendLine(lowVersionRiskTip).setFontSize(textSize, true).setLineHeight(lineHeightItem)
                .setForegroundColor(ResourceUtils.getColor(R.color.frame_error_text_color))
        }
        return span.appendLine(newVersionGeneralInfo)
            .setFontSize(textSize, true)
            .setLineHeight(lineHeightTitle)
            .setForegroundColor(ResourceUtils.getColor(R.color.gray_66))
            .append(ver.desc)
            .setFontSize(textSize, true)
            .setLineHeight(lineHeightItem)
            .setForegroundColor(ResourceUtils.getColor(R.color.gray_99))
            .create()
    }
}