
#云ocr依赖的webank normal库混淆规则
-include webank-cloud-normal-proguard-rules.pro

#不混淆内部类

######################云ocr混淆规则 ocr-BEGIN###########################
-keepattributes InnerClasses
-keep public class com.webank.mbank.ocr.WbCloudOcrSDK{
    public <methods>;
    public static final *;
}
-keep public class com.webank.mbank.ocr.WbCloudOcrSDK$*{
    *;
}

-keep public class com.webank.mbank.ocr.tools.ErrorCode{
    *;
}
-keep public class com.webank.mbank.ocr.tools.CameraGlobalDataUtils{
    *;
}
-keep public class com.webank.mbank.ocr.tools.Utils{
    *;
}

-keep public class com.webank.mbank.ocr.net.*$*{
    *;
}
-keep public class com.webank.mbank.ocr.net.*{
    *;
}


#######################云ocr混淆规则 ocr-END#############################
