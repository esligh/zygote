package com.yunzhu.module.common.utils;

import android.Manifest;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.yunzhu.module.config.AppConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;



/**
 * Created by Administrator on 2019-05-24.
 */
public class DeviceUtil {

    private DeviceUtil() {

    }

    /**
     * 获取设备指纹
     * @param context
     * @return
     */
    public static String getDevFingerprint(Context context) {
        //0:1|2001:aaa|2002:aaa|2003: .....
        StringBuffer buffer = new StringBuffer();
        buffer.append("0:1");
        filterAppend(buffer, "2001", getIMEI(context));//IMEI
        filterAppend(buffer, "2002", getIMSI(context));//IMSI
        filterAppend(buffer, "2003", getMac(context));//MAC
        filterAppend(buffer, "2004", Build.SERIAL); //SN序列号
        filterAppend(buffer, "2006", getAndroidId(context));//ANDROID_ID
//        filterAppend(buffer, "918", AntiEmulator.isEmulator(context) ? "1" : "0");//是否在模拟器中
//        filterAppend(buffer, "919", rootState());//是否root
        filterAppend(buffer, "2010", String.valueOf(isSupportMultiTouch(context)));//支持多点触控
        filterAppend(buffer, "2013", getScreenSizeOfDevice(context));//几寸屏
        filterAppend(buffer, "2014", getWidthAndHeight(context));//分辨率
        filterAppend(buffer, "2015", Build.MODEL);

        filterAppend(buffer, "2016", Build.PRODUCT);//产品
        filterAppend(buffer, "2018", "android");
        filterAppend(buffer, "2019", Build.VERSION.RELEASE);
//        filterAppend(buffer, "2020", Locale.getDefault().getLanguage());//语言
//        filterAppend(buffer, "2021", context.getPackageName());//包名
//        filterAppend(buffer, "2022", getVersionNames(context));//版本名称
        filterAppend(buffer, "2023", getChannelCode(context));//配置的渠道号
//        filterAppend(buffer, "2024", Build.VERSION.SDK);//Android SDK版本
//        filterAppend(buffer, "2025", getUseMemory(context));//可用内存
//        filterAppend(buffer, "2026", Formatter.formatFileSize(context, getTotalMemory()));
        filterAppend(buffer, "2027", currentPhone(context));
        filterAppend(buffer, "2028", baseBand());//基带
        filterAppend(buffer, "2029", getKernelVersion());//内核版本
        filterAppend(buffer, "920", Locale.getDefault().getCountry());
//        filterAppend(buffer, "921", getTimeZone());//时区
//        filterAppend(buffer, "922", getLocalIpAddress(context)); //能够获取到3g的IP
//        filterAppend(buffer, "923", getDNS(context));//DNS
//        filterAppend(buffer, "924", getTMobile(context));//运营商
        filterAppend(buffer, "925", getMCC(context));//MCC
        filterAppend(buffer, "926", getMNC(context));//MNC
        filterAppend(buffer, "927", getBSSID(context));//BSSID
//        filterAppend(buffer, "928", getSSID(context));//2038

//        filterAppend(buffer, "2051", getSDSize(context)); //磁盘信息
        filterAppend(buffer, "2052", getCpuInfo()); //CPU型号
        filterAppend(buffer, "2053", getBoardInfo());//主板
        filterAppend(buffer, "2054", Build.DEVICE);//驱动
//        filterAppend(buffer, "2055", getCurrentNetType(context));//网络类型
//        filterAppend(buffer, "2057", getICCID(context));//SIM序列号
//        filterAppend(buffer, "2058", getBluetoothMac(context));//蓝牙Mac
//        filterAppend(buffer, "2059", getLocation(context));

        return buffer.toString();
    }

    /**
     * 设备uuid生成规则： 不能依赖硬件的有无，如SIM卡，不能依赖权限
     * 收集设备的固定信息，通过hash生成
     * */
    public static String getDeviceUUID(Context context)
    {
        String androidId = getAndroidId(context); //Android ID
        String boardInfo = getBoardInfo(); //主板信息
        String cpuAbi = android.os.Build.CPU_ABI; //CPU指令集
        String size = getWidthAndHeight(context); //分辨率

        StringBuilder deviceInfo = new StringBuilder();
        deviceInfo.append(boardInfo).append("/");
        deviceInfo.append(cpuAbi).append("/");
        deviceInfo.append(size).append("/");
        deviceInfo.append(Build.DEVICE).append("/");
        deviceInfo.append(Build.PRODUCT).append("/");
        deviceInfo.append(Build.MANUFACTURER).append("/");
        deviceInfo.append(Build.HARDWARE).append("/");
        deviceInfo.append(Build.ID).append("/");
        deviceInfo.append(Build.VERSION.INCREMENTAL).append("/");
        deviceInfo.append(System.getProperty("http.agent"));
        //要求：uuid不带“-”且32位
        return new UUID(androidId.hashCode(), deviceInfo.toString().hashCode()).toString().replace("-","");
    }


    /**
     * IMEI(手机设备识别码)
     * @return
     */
    public static String getIMEI(Context context) {
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCpuInfo() {
        try {
            String str = "";
            String[] arrayOfString1 = {"", ""};
            try {
                BufferedReader localBufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"), 8192);
                String[] arrayOfString2 = localBufferedReader.readLine().split("\\s+");
                for (int i = 2; ; i++) {
                    if (i >= arrayOfString2.length) {
                        String[] arrayOfString3 = localBufferedReader.readLine().split("\\s+");
                        arrayOfString1[1] = (arrayOfString1[1] + arrayOfString3[2]);
                        str = arrayOfString1[0] + arrayOfString1[1];
                        localBufferedReader.close();
                        return str;
                    }
                    arrayOfString1[0] = (arrayOfString1[0] + arrayOfString2[i] + " ");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getCurrentNetType(Context context) {
        try {
            String type = "";
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null) {
                return null;
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                type = "wifi";
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                int subType = info.getSubtype();
                if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
                        || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                    type = "2g";
                } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                        || subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                        || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                    type = "3g";
                } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准
                    type = "4g";
                }
            }
            return type;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return 获取主板信息
     */
    public static String getBoardInfo() {
        return new StringBuilder("BOARD: ").append(Build.BOARD)
                .append(", BOOTLOADER: ").append(Build.BOOTLOADER).toString();
    }

    /**
     * IMSI(国际移动用户识别码)
     * @return
     */
    public static String getIMSI(Context context) {
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                String im = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
                return im == null ? "" : im;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**获取时区
     * @return
     */
    public static String getTimeZone() {
        try {
            TimeZone tz = TimeZone.getDefault();
            return "TimeZone   " + tz.getDisplayName(false, TimeZone.SHORT) + "Timezon id :: " + tz.getID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @return 获取运营商
     */
    public static String getTMobile(Context context) {
        try {
            String t = "";
            try {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    t = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId().substring(0, 5);
                }
            } catch (Exception e) {
                t = "0";
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return 获取MCC
     * 注意：需要读取设备状态的权限
     */
    public static String getMCC(Context context) {
        try {
            String t = "";
            try {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    t = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId().substring(0, 3);
                }
            } catch (Exception e) {
                t = "0";
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取MNC
     * @return
     */
    public static String getMNC(Context context) {
        try {
            String t = "";
            try {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    t = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId().substring(3, 5);
                }
            } catch (Exception e) {
                t = "0";
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取基带版本
     * @return
     */
    public static String baseBand() {
        try {
            Class cl = Class.forName("android.os.SystemProperties");
            Object invoker = cl.newInstance();
            Method m = cl.getMethod("get", new Class[]{String.class, String.class});
            Object result = m.invoke(invoker, new Object[]{"gsm.version.baseband", "no message"});
            return (String) result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取可使用内存
     *
     * @return
     */
    public static String getUseMemory(Context context) {
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(mi);
            long l = mi.availMem;
            long t = getTotalMemory();
            return Formatter.formatFileSize(context, (t - l));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取总内存
     *
     * @return
     */
    private static long getTotalMemory() {
        try {
            String str1 = "/proc/meminfo";// 系统内存信息文件
            String str2;
            String[] arrayOfString;
            long initial_memory = 0;
            try {
                FileReader localFileReader = new FileReader(str1);
                BufferedReader localBufferedReader = new BufferedReader(
                        localFileReader, 8192);
                str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
                arrayOfString = str2.split("\\s+");

                if (arrayOfString.length > 1) {
                    initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
                }
                localBufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return initial_memory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String currentPhone(Context context) {
        try {
            return context.getApplicationContext().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED))
                    .getIntExtra(BatteryManager.EXTRA_LEVEL, 0) + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getScreenSizeOfDevice(Context context) {
        try {
            DisplayMetrics localDisplayMetrics = context.getResources().getDisplayMetrics();
            int i = localDisplayMetrics.widthPixels;
            int j = localDisplayMetrics.heightPixels;
            return String.format("%.1f", Math.sqrt(Math.pow(i, 2.0D) + Math.pow(j, 2.0D)) / localDisplayMetrics.densityDpi);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isSupportMultiTouch(Context context) {
        try {
            return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @return 获取wifi mac地址
     * 注意：wifi未启动过则获取不到
     */
    public static String getMac(Context context) {
        try {
            WifiManager localWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (!localWifiManager.isWifiEnabled()) {
                return null;
            }
            return localWifiManager.getConnectionInfo().getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getBSSID(Context context) {
        try {
            WifiManager localWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (!localWifiManager.isWifiEnabled()) {
                return null;
            }
            return localWifiManager.getConnectionInfo().getBSSID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String getSSID(Context context) {
        try {
            WifiManager localWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (!localWifiManager.isWifiEnabled()) {
                return null;
            }
            return localWifiManager.getConnectionInfo().getSSID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get SIM card
     * @return
     */
    public static String getICCID(Context context) {
        try {
            TelephonyManager tel = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                return tel.getSimSerialNumber(); //ICCID
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getBluetoothMac(Context context) {
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADMIN)
                    == PackageManager.PERMISSION_GRANTED) {
                BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
                if (btAdapter.isEnabled()) {
                    return btAdapter.getAddress();
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取IP
     * @return
     */
    public static String getLocalIpAddress(Context context) {
        WifiManager wifimanage = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);//获取WifiManager
        //检查wifi是否开启
        if (wifimanage.isWifiEnabled()) { // 没开启wifi时,ip地址为0.0.0.0
            Log.d("===", "=====!!!!");
            return getIp(context);
        }
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();

                    if (!inetAddress.isLoopbackAddress()) {
                        Log.d("===", "=====" + inetAddress.getHostAddress().toString());
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getIp(Context context) {
        WifiManager localWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//       if (!localWifiManager.isWifiEnabled())
//            localWifiManager.setWifiEnabled(true);
        int i = localWifiManager.getConnectionInfo().getIpAddress();
        return (i & 0xFF) + "." + (0xFF & i >> 8) + "." + (0xFF & i >> 16) + "." + (0xFF & i >> 24);
    }

    public static String getDNS(Context context) {
        try {
            WifiManager localWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            DhcpInfo localDhcpInfo = localWifiManager.getDhcpInfo();
            localWifiManager.getConnectionInfo();
            return intToString(localDhcpInfo.dns1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String intToString(int paramInt) {
        return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) + "." + (0xFF & paramInt >> 16) + "."
                + (0xFF & paramInt >> 24);
    }

    public static String getWidthAndHeight(Context context) {
        DisplayMetrics localDisplayMetrics = context.getResources().getDisplayMetrics();
        int width = localDisplayMetrics.widthPixels;
        int height = localDisplayMetrics.heightPixels;
        return String.format("%d * %d",width,height);
    }

    public static String getVersionNames(Context context) {
        try {
            String str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            return str;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return null;
    }

    public static String getChannelCode(Context paramContext) {
        try {
            String str = getMetaData(paramContext, "CHANNEL");
            if (str != null)
                return str;
            return "322d4985062b16";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMetaData(Context paramContext, String paramString) {
        try {
            Object localObject = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(),
                    PackageManager.GET_META_DATA).metaData.get(paramString);
            if (localObject != null) {
                String str = localObject.toString();
                return str;
            }
        } catch (Exception localException) {
        }
        return null;
    }

    /**
     * 当设备在第一次启动时，系统会随机产生一个64位的数字然后以16进制的形式保存在设备上
     * @return Android ID
     * 注意：Android ID在一些设备上可能为空，重新刷机后可能发生变化
     */
    public static String getAndroidId(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Boolean rootState() {
        try {
            return RootDetect.isRootSystem();//new Root().isDeviceRooted();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return 内核版本
     * 注意：内核版本可能变化
     */
    public static String getKernelVersion() {
        try {
            String kernelVersion = "";
            InputStream inputStream;
            try {
                inputStream = new FileInputStream("/proc/version");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return kernelVersion;
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 8 * 1024);
            String info = "";
            String line = "";
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    info += line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bufferedReader.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                if (info != "") {
                    final String keyword = "version ";
                    int index = info.indexOf(keyword);
                    line = info.substring(index + keyword.length());
                    index = line.indexOf(" ");
                    kernelVersion = line.substring(0, index);
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            return kernelVersion;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取sd卡容量大小
    public static String getSDSize(Context context) {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File path = Environment.getExternalStorageDirectory();//得到SD卡的路径
                StatFs stat = new StatFs(path.getPath());//创建StatFs对象，用来获取文件系统的状态
                long blockCount = stat.getBlockCount();
                long blockSize = stat.getBlockSize();
                String totalSize = Formatter.formatFileSize(context, blockCount * blockSize);//格式化获得SD卡总容量
                return totalSize;
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static StringBuffer filterAppend(StringBuffer buffer, String key, String value) {
        if (value == null || value.isEmpty()) {
            return buffer;
        }

        //过滤特殊字符  &&   过滤指定指定字符
        value = checkSpecialChar(value);
        value = checkSpecificChar(value);

        if (value.indexOf("|") > -1) {
            value = value.replace("|", "-");
        }

        if (value.indexOf('\n') > -1 || value.indexOf('\r') > -1) {
            value = value.replace('\n', ' ').replace('\r', ' ');
        }

        buffer.append("|").append(key).append(":").append(value);

        return buffer;
    }

    /**
     * 判断是否存在特殊字符，如果首尾都存在则返回空，否则返回自己
     * @param
     * @return
     */
    private static String checkSpecialChar(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        final Pattern mPattern = Pattern.compile("[\u0000-\u001f]");
        boolean rsFirst = mPattern.matcher(str.substring(0, 1)).find();
        boolean rsLast = mPattern.matcher(str.substring(str.length() - 1)).find();
        if (rsFirst && rsLast) {
            return null;
        }

        boolean rsWhole = mPattern.matcher(str).find();
        if (rsWhole) {
            return str.replaceAll("[\u0000-\u001f]", "");
        }
        return str.trim();

    }

    /**
     * 检测包含指定符号
     * @param str
     * @return
     */
    private static String checkSpecificChar(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Pattern mPattern = Pattern.compile("[$<>\"\']");
        boolean isMatch = mPattern.matcher(str).find();
        if (isMatch) {
            return str.replaceAll("[$<>\"\']", "");
        }
        return str.trim();
    }

    public static String getLocation(Context context) {
        StringBuffer result = new StringBuffer();
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        String locationProvider = null;
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER;
        }
        if (locationProvider != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location location = locationManager.getLastKnownLocation(locationProvider);
                if (location != null) {
                    double lang = location.getLongitude();//经度
                    double lat = location.getLatitude();//纬度
                    result.append(String.valueOf(lang)).append(",").append(String.valueOf(lat));
                }
            }
        }
        return result.toString();
    }


    public static void updateLocation(Context context, LocationListener locationListener) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        String locationProvider = null;
        if (providers != null && providers.contains(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers != null && providers.contains(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER;
        }
        if (locationProvider != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(locationProvider, 1000, 1, locationListener);
        }
    }
}
