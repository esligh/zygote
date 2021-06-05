package com.yunzhu.module.main.ui.fragment

import android.Manifest
import android.os.Environment
import android.util.Log
import com.amap.api.mapcore.util.`in`
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yunzhu.module.`fun`.socialize.entity.ShareInfo
import com.yunzhu.module.`fun`.socialize.ui.dialog.BottomGridShareDialog
import com.yunzhu.module.bus.common.base.FrameBaseFragment
import com.yunzhu.module.bus.common.vm.EmptyViewModel
import com.yunzhu.module.bus.constant.page.Page
import com.yunzhu.module.common.extensions.Router
import com.yunzhu.module.main.R
import kotlinx.android.synthetic.main.main_fragment_home.*
import java.io.*


class HomeFragment : FrameBaseFragment<EmptyViewModel>() {

    override fun getLayoutId(): Int = R.layout.main_fragment_home

    override fun initView() {
    }

    override fun setListener() {
        scanQrCodeTv.setOnClickListener {
            val rxPermissions = RxPermissions(requireActivity())
            rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe {
                    if(it){
                        Router.goPageForResult(requireActivity(),Page.CommonModule.SCAN_QR_CODE,100)
                    }
                }
        }

        shareTv.setOnClickListener {
            val shareInfo = ShareInfo()
            shareInfo.title = "My App"
            shareInfo.shareUrl = "https://www.xxx.com"
            shareInfo.copyWriting = "this is a test"
            BottomGridShareDialog(requireActivity(),shareInfo).show()
        }

        readTv.setOnClickListener {
            val rxPermissions = RxPermissions(requireActivity())
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe {
                    if(it){
                        read()
                    }
                }
        }

        writeTv.setOnClickListener {
            val rxPermissions = RxPermissions(requireActivity())
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe {
                    if(it){
                        write()
                    }
                }
        }

        deleteTv.setOnClickListener {
            val str = getExternalStorageDirectoryPath() + "/ssss/"
            val dir = File(str)
            if(!dir.exists()){
                dir.mkdirs()
            }
            val file = File(dir,"123.zip")
            if(file.exists()){
                Log.d("sss","exists,prepare delete")
                val result = file.delete()
                Log.d("sss", "delete result==>$result")
            }else{
                Log.d("sss","not found file")
            }
        }
    }

    private fun write()
    {
        Thread{
            var n = 0
            val str = getExternalStorageDirectoryPath() + "/ssss/"
            val dir = File(str)
            if(!dir.exists()){
                dir.mkdirs()
            }
            val file = File(dir,"1111.txt")
            val os = BufferedOutputStream(FileOutputStream(file), 32768)
            while (true) {
                os.write("hello\n".toByteArray())
                os.flush()
                Log.d("sss","write over,n=$n,sleep now... ")
                Thread.sleep(2000)
                n++
                if(n>=10){
                    break
                }
            }
            os.close()
        }.start()
    }

    private fun getExternalStorageDirectoryPath(): String? {
        return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            Environment.getExternalStorageDirectory().absolutePath
        } else null
    }


    private fun read()
    {
        Thread{
            val fileName = "/sdcard/ssss/123.zip"
            val file = File(fileName)
            if(file.exists()){
                Log.d("sss","exists,prepare read... ")

                val tempbytes = ByteArray(10)
                var byteread = 0
                val inputStream = FileInputStream(fileName)
                while (inputStream.read(tempbytes).also { byteread = it } != -1) {
                    Log.d("sss", "read content=${tempbytes.size},sleep now..")
                    Thread.sleep(2000)
                }
            }
        }.start()
    }

}