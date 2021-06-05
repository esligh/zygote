package com.yunzhu.module.`fun`.socialize.ui.dialog

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import cn.sharesdk.framework.ShareSDK
import com.yunzhu.module.`fun`.socialize.R
import com.yunzhu.module.`fun`.socialize.common.AnalysisHelper
import com.yunzhu.module.`fun`.socialize.entity.ShareIcon
import com.yunzhu.module.`fun`.socialize.entity.ShareInfo
import com.yunzhu.module.base.tools.adapter.CommonAdapter
import com.yunzhu.module.base.tools.adapter.CommonViewHolder
import com.yunzhu.module.base.tools.helper.AppCheckerHelper
import kotlinx.android.synthetic.main.module_fun_socialize_dialog_share_bottom.*
import java.util.HashMap

/**
 * @author: lisc
 * @date: 2020-09-07 下午 04:13
 * @desc: 自定义分享弹框
 */
class BottomGridShareDialog : Dialog {
    private var mContext: Context ?= null
    private var mAdapter: CommonAdapter<ShareIcon>? = null
    private var mData = ArrayList<ShareIcon>()
    private var mShareInfo: ShareInfo

    companion object{
        const val WECHAT_TAG = 0
        const val WECHAT_MOMENT_TAG = 1
        const val QQ_TAG = 2
        const val COPY_LINK_TAG = 3
    }

    constructor(context: Context, shareInfo: ShareInfo) : super(context, R.style.dialog_bottom_full) {
        this.mContext = context
        this.mShareInfo = shareInfo

        var wechat = ShareIcon()
        wechat.tag = WECHAT_TAG
        wechat.resId = R.drawable.image_wechat
        wechat.name = mContext!!.getString(R.string.module_fun_socialize_share_wechat)
        mData.add(wechat)

        var moment = ShareIcon()
        moment.tag = WECHAT_MOMENT_TAG
        moment.resId = R.drawable.image_wechat_moments
        moment.name = mContext!!.getString(R.string.module_fun_socialize_share_wechat_moment)
        mData.add(moment)

        var qq = ShareIcon()
        qq.tag = QQ_TAG
        qq.resId = R.drawable.image_qq
        qq.name = mContext!!.getString(R.string.module_fun_socialize_share_qq)
        mData.add(qq)

        var copy = ShareIcon()
        copy.tag = COPY_LINK_TAG
        copy.resId = R.drawable.image_link
        copy.name = mContext!!.getString(R.string.module_fun_socialize_share_copy_link)
        mData.add(copy)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = window
        window?.setGravity(Gravity.BOTTOM)
        window?.setWindowAnimations(R.style.dialogAnimation)
        setCanceledOnTouchOutside(true)
        setContentView(R.layout.module_fun_socialize_dialog_share_bottom)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        mAdapter = object :CommonAdapter<ShareIcon>(R.layout.module_fun_socialize_item_grid_share,mData){
            override fun convert(holder: CommonViewHolder?, item: ShareIcon?, position: Int) {
                holder?.let { holder ->
                    item?.let {
                        holder.setImageResource(R.id.iconIv,item.resId)
                        holder.setTvText(R.id.nameTv,item.name)
                    }
                }
            }
        }
        shareGridList.adapter = mAdapter
        shareGridList.setOnItemClickListener { _, _, pos, _ ->
            dismiss()
            if(checkPackage(mData[pos])) {
                shareApp(mData[pos])
            }
        }
        cancelBtn.setOnClickListener { dismiss() }
    }

    private fun shareApp(shareIcon:ShareIcon)
    {
        AnalysisHelper.appShareType(mContext!!,getPlatform(shareIcon.tag))
        when(shareIcon.tag){
            QQ_TAG -> {
                val sp = Platform.ShareParams()
                sp.title = mShareInfo.title
                sp.titleUrl = mShareInfo.shareUrl // 标题的超链接
                sp.text = mShareInfo.copyWriting
                sp.imageUrl = mShareInfo.icon
                sp.comment = mShareInfo.copyWriting
                sp.site = ""
                sp.siteUrl = mShareInfo.shareUrl
                val qq: Platform = ShareSDK.getPlatform("QQ")
                qq.platformActionListener = platformActionListener
                qq.share(sp)
            }
            COPY_LINK_TAG -> {
                var cm = mContext!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val mClipData = ClipData.newPlainText("Label", mShareInfo.shareUrl)
                cm.setPrimaryClip(mClipData)
                Toast.makeText(mContext,mContext!!.getString(R.string.module_fun_socialize_copy_link_success)
                    ,Toast.LENGTH_SHORT).show()
            }
            else -> {
                var plat = ShareSDK.getPlatform(getPlatform(shareIcon.tag))
                plat.platformActionListener = platformActionListener
                var sp = Platform.ShareParams()
                sp.shareType = Platform.SHARE_WEBPAGE
                sp.title = mShareInfo.title
                sp.text = mShareInfo.copyWriting
                sp.url = mShareInfo.shareUrl
                sp.imageUrl = mShareInfo.icon
                plat.share(sp)
            }
        }
    }

    private fun checkPackage(shareIcon:ShareIcon):Boolean
    {
        return if(shareIcon.tag == QQ_TAG){
            val result = AppCheckerHelper.checkQQAvailable(mContext!!)
            if(!result){
                Toast.makeText(mContext,mContext!!.getString(R.string.module_fun_socialize_qq_not_install)
                    ,Toast.LENGTH_SHORT).show()
            }
            return result
        }else if(shareIcon.tag == WECHAT_MOMENT_TAG || shareIcon.tag == WECHAT_TAG) {
            val result = AppCheckerHelper.checkWeChatAvailable(mContext!!)
            if(!result){
                Toast.makeText(mContext,mContext!!.getString(R.string.module_fun_socialize_wx_not_install)
                    ,Toast.LENGTH_SHORT).show()
            }
            return result
        }else shareIcon.tag == COPY_LINK_TAG
    }

    private var platformActionListener = object : PlatformActionListener {
        override fun onComplete(platform: Platform?, p1: Int, p2: HashMap<String, Any>?) {
            //  埋点统计
//            AnalysisHelper.appShareValid(mContext!!,platform?.name?:"")
        }

        override fun onCancel(p0: Platform?, p1: Int) {

        }

        override fun onError(p0: Platform?, p1: Int, p2: Throwable?) {
            Log.d("share","code="+p1+",p2="+p2?.message)
        }
    }

    private fun getPlatform(tag: Int): String {
        var platform = ""
        when (tag) {
            0 -> platform = "Wechat"
            1 -> platform = "WechatMoments"
            2 -> platform = "QQ"
        }
        return platform
    }


}