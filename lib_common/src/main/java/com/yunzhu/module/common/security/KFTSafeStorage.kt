//package com.yunzhu.module.common.security
//
//import android.os.Build
//import android.text.TextUtils
//import com.yunzhu.module.common.base.BaseApplication
//import com.yunzhu.module.common.utils.AESCrypt
//import com.yunzhu.module.common.utils.PreferencesUtils
//import com.yunzhu.module.config.AppConfig
//import java.util.*
//
///**
// * @author:lisc
// * @date:2020-04-30
// * @see: SafePreferenceUtil，KeyStoreHelper class
// * @desc:快付通安全存储，基于KeyStore和SafeSharePreference开发
// * Android M及以上版本 使用SafeSharePreference
// * Android M以下版本，基于KeyStore+AES，需要首先为设备随机生成AES密钥(MASTER_KEY)，MASTER_KEY通过KeyStore
// * 加密后存储于SP中，每次需要加密存储的数据，需要先取到MASTER_KEY，再通过AES加密存储于SP中
// * 注意：当设备起始版本小于M，后面通过升级更新到M或以后版本，安全存储策略不会发生变化，依旧为KeyStore+AES，
// * 直到应用卸载重新安装
// * */
//object KFTSafeStorage {
//
//    /**
//     * Android M以下版本调用该方法生成主Key
//     * */
//    fun generateMaterKeyBelowM()
//    {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            val mainKey = PreferencesUtils.getString(BaseApplication.Companion.instance,
//                AppConfig.PrefKey.MASTER_KEY, "")
//            val keyAlias = AppConfig.KeyAlias.MASTER_KEY
//            if (TextUtils.isEmpty(mainKey)) {
//                try {
//                    KeyStoreHelper.genKeyStoreKey(BaseApplication.Companion.instance, keyAlias)
//                    val key = KeyStoreHelper.generatorMasterKey(256, Calendar.getInstance().timeInMillis)
////                L.d("test", "key=" + key + ",len=" + key.length)
//                    val masterKey = KeyStoreHelper.encryptRSA(keyAlias, key)
////                L.d("test", "rsa key=" + masterKey + ",len=" + masterKey.length)
//                    PreferencesUtils.putString(BaseApplication.Companion.instance,
//                        AppConfig.PrefKey.MASTER_KEY, masterKey)
//                } catch (e: Exception) { }
//            }
//        }
//    }
//
//    fun putString(prefKey: String, value: String) {
//        var mainKey = PreferencesUtils.getString(BaseApplication.instance.applicationContext,
//            AppConfig.PrefKey.MASTER_KEY, "")
//
//        if(!TextUtils.isEmpty(mainKey)){
////            L.d("test","master key src=$mainKey,len="+mainKey.length)
//            var key = KeyStoreHelper.decryptRSA(AppConfig.KeyAlias.MASTER_KEY,mainKey)
////            L.d("test","master key=$key,len="+key.length)
//            var result = AESCrypt.encrypt(key,value)
////            L.d("test","save data =$result,len="+result.length)
//            PreferencesUtils.putString(BaseApplication.instance.applicationContext,
//                prefKey,result)
//        } else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                SafePreferenceUtil.putString(prefKey, value)
//            }
//        }
//    }
//
//    fun getString(prefKey: String): String {
//        var mainKey = PreferencesUtils.getString(BaseApplication.instance.applicationContext,
//            AppConfig.PrefKey.MASTER_KEY, "")
//        if(!TextUtils.isEmpty(mainKey)){
////            L.d("test","master key src=$mainKey,len="+mainKey.length)
//            var key = KeyStoreHelper.decryptRSA(AppConfig.KeyAlias.MASTER_KEY,mainKey)
////            L.d("test","master key=$key,len="+key.length)
//            var data = PreferencesUtils.getString(BaseApplication.instance.applicationContext,
//                prefKey,"")
////            L.d("test","get data =$data,len="+data.length)
//            return AESCrypt.decrypt(key,data)
//        }else{
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                return SafePreferenceUtil.getString(prefKey,"")
//            }
//        }
//        return ""
//    }
//
//    fun removeKey(key:String)
//    {
//        var mainKey = PreferencesUtils.getString(BaseApplication.instance.applicationContext,
//            AppConfig.PrefKey.MASTER_KEY, "")
//
//        if(!TextUtils.isEmpty(mainKey)){
//            PreferencesUtils.removeKey(BaseApplication.instance.applicationContext,key)
//        }else{
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                return SafePreferenceUtil.removeKey(key)
//            }
//        }
//    }
//}