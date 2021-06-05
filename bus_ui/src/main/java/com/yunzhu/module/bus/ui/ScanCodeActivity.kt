package com.yunzhu.module.bus.ui

import android.app.Activity
import android.content.Intent
import android.os.Vibrator
import android.view.View
import androidx.core.content.ContextCompat
import cn.bingoogolapple.qrcode.core.QRCodeView
import com.alibaba.android.arouter.facade.annotation.Route
import com.yunzhu.module.bus.common.base.FrameBaseActivity
import com.yunzhu.module.bus.common.vm.ScanCodeViewModel
import com.yunzhu.module.bus.constant.ExtraName
import com.yunzhu.module.bus.constant.page.Page
import com.yunzhu.module.ui.extensions.UiExt
import kotlinx.android.synthetic.main.frame_activity_scan_qr_code.*

/**
 * @author：lisc
 * @date: 2020-03-27
 * @desc:扫码页面
 * @see: https://github.com/bingoogolapple/BGAQRCode-Android
 * */
@Route(path = Page.CommonModule.SCAN_QR_CODE)
class ScanCodeActivity : FrameBaseActivity<ScanCodeViewModel>(), QRCodeView.Delegate {

    companion object{
        const val VIBRATE_TIME = 200L

    }

    private var bLightOn:Boolean = false
    override fun showToolbar(): Boolean  = false
    override fun isDarkTheme(): Boolean = false

    override fun getLayoutId(): Int  = R.layout.frame_activity_scan_qr_code

    override fun initView() {
        UiExt.setFullScreen(this)
        UiExt.fitNotchScreen(window)
        toolbarTitle.text  = getString(R.string.frame_scan_code)
        zXingView.setDelegate(this)
    }

    override fun setListener() {
        lightTv.setOnClickListener {
            if (bLightOn) {
                lightTv.setCompoundDrawablesWithIntrinsicBounds(null,
                    ContextCompat.getDrawable(this,R.drawable.ic_light_off),null, null)
                lightTv.text = getString(R.string.frame_scan_light_on_tips)
                zXingView.closeFlashlight()
            } else {
                lightTv.setCompoundDrawablesWithIntrinsicBounds(null,
                    ContextCompat.getDrawable(this,R.drawable.ic_light_on), null, null)
                lightTv.text = getString(R.string.frame_scan_light_off_tips)
                zXingView.openFlashlight()
            }
            bLightOn = !bLightOn
        }

        photoAlbumTv.setOnClickListener {
            /*  val photoPickerIntent = BGAPhotoPickerActivity.IntentBuilder(this)
                .cameraFileDir(null)
                .maxChooseCount(1)
                .selectedPhotos(null)
                .pauseOnScroll(false)
                .build()
            startActivityForResult(photoPickerIntent, REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY)*/
        }
    }

    override fun onStart() {
        super.onStart()
        zXingView.startCamera() // 打开后置摄像头开始预览，但是并未开始识别
        zXingView.startSpotAndShowRect() // 显示扫描框，并开始识别
    }

    override fun onScanQRCodeSuccess(result: String?) {
        vibrate()
        val data = Intent()
        data.putExtra(ExtraName.Common.QR_RESULT_CODE,result)
        setResult(Activity.RESULT_OK,data)
        closePage()
    }

    private fun vibrate() {
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        if(vibrator.hasVibrator()){
            vibrator.vibrate(VIBRATE_TIME)
        }
    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
        if (isDark || bLightOn) {
            lightTv.visibility = View.VISIBLE
        } else {
            lightTv.visibility = View.GONE
        }
    }

    override fun onScanQRCodeOpenCameraError() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        zXingView.startSpotAndShowRect()
        /*  if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
            val picturePath = BGAPhotoPickerActivity.getSelectedPhotos(data)[0]
            zXingView.decodeQRCode(picturePath)
        }*/
    }

    override fun onStop() {
        super.onStop()
        zXingView.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        zXingView.onDestroy()
    }
}
