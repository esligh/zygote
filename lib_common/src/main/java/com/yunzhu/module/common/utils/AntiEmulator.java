package com.yunzhu.module.common.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

public class AntiEmulator {
    private static String[] known_pipes = {
            "/dev/socket/qemud",
            "/dev/qemu_pipe"
    };

    private static String[] known_qemu_drivers = {
            "goldfish"
    };

    private static String[] known_files = {
            "/system/lib/libc_malloc_debug_qemu.so",
            "/sys/qemu_trace",
//            "/system/bin/qemu-props"
    };

    public static boolean isEmulator(Context context) {
        try {
            if (checkByRuleOfGoogleAD()
                    || checkID(context)
                    || checkPipes()
                    || checkQEmuDriverFile()
                    || CheckEmulatorBuild(context)
                    || CheckEmulatorFiles()
                    || CheckOperatorNameAndroid(context)) {
                return true;
            }
        } catch (Exception ioe) {
            //捕获异常 则说明是虚拟机
            return true;
        }
        return false;
    }


    protected static boolean checkID(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            String imei = tm.getDeviceId();
            String phoneNum = tm.getLine1Number();
            if (imei != null && imei.equals("000000000000000")) {
                return true;
            }
            if (phoneNum != null && phoneNum.equals("15555215554")){
                return true;
            }
        }
        return false;
   }


   //检测“/dev/socket/qemud”，“/dev/qemu_pipe”这两个通道
   protected static boolean checkPipes(){
       for(int i = 0; i < known_pipes.length; i++){
           String pipes = known_pipes[i];
           File qemu_socket = new File(pipes);
           if(qemu_socket.exists()){
               Log.v("Result:", "Find pipes!");
               return true;
           }
       }
       Log.i("Result:", "Not Find pipes!");
       return false;
   }

   // 检测驱动文件内容
   // 读取文件内容，然后检查已知QEmu的驱动程序的列表
   protected static Boolean checkQEmuDriverFile(){
       File driver_file = new File("/proc/tty/drivers");
       if(driver_file.exists() && driver_file.canRead()){
           byte[] data = new byte[1024];  //(int)driver_file.length()
           try {
               InputStream inStream = new FileInputStream(driver_file);
               inStream.read(data);
               inStream.close();
           } catch (Exception e) {
               // TODO: handle exception
               e.printStackTrace();
           }
           String driver_data = new String(data);
           for(String known_qemu_driver : AntiEmulator.known_qemu_drivers){
               if(driver_data.indexOf(known_qemu_driver) != -1){
                   Log.i("Result:", "Find know_qemu_drivers!");
                   return true;
               }
           }
       }
       Log.i("Result:", "Not Find known_qemu_drivers!");
       return false;
   }

   //检测模拟器上特有的几个文件
   protected static Boolean CheckEmulatorFiles(){
       for(int i = 0; i < known_files.length; i++){
           String file_name = known_files[i];
           File qemu_file = new File(file_name);
           if(qemu_file.exists()){
               Log.v("Result:", "Find Emulator Files!::" + file_name);
               return true;
           }
       }
       Log.v("Result:", "Not Find Emulator Files!");
       return false;
   }


   //检测手机上的一些硬件信息
   protected static Boolean CheckEmulatorBuild(Context context){
       String BOARD = android.os.Build.BOARD;
       String BOOTLOADER = android.os.Build.BOOTLOADER;
       String BRAND = android.os.Build.BRAND;
       String DEVICE = android.os.Build.DEVICE;
       String HARDWARE = android.os.Build.HARDWARE;
       String MODEL = android.os.Build.MODEL;
       String PRODUCT = android.os.Build.PRODUCT;
       if (BOARD == "unknown" || BOOTLOADER == "unknown"
               || BRAND == "generic" || DEVICE == "generic"
               || MODEL.contains("sdk") ||MODEL.contains("vbox") ||PRODUCT == "sdk"
               || HARDWARE == "goldfish")
       {
           Log.v("Result:", "Find Emulator by EmulatorBuild!");
           return true;
       }
       Log.v("Result:", "Not Find Emulator by EmulatorBuild!");
       return false;
   }

   //检测手机运营商家
   protected static boolean CheckOperatorNameAndroid(Context context) {
       String szOperatorName = ((TelephonyManager)
               context.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkOperatorName();

       if (szOperatorName.toLowerCase().equals("android") == true) {
           Log.v("Result:", "Find Emulator by OperatorName!");
           return true;
       }
       Log.v("Result:", "Not Find Emulator by OperatorName!");
       return false;
   }

   /**
    * 通过谷歌ad的方法进行检测
    */
   protected static boolean checkByRuleOfGoogleAD() {
       try {
           Method localMethod = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[]{String.class});
           localMethod.setAccessible(true);
           boolean bool = "1".equals(localMethod.invoke(null, new Object[]{"ro.kernel.qemu"}));
           return bool;
       }  catch (Exception e) {}
       return false;
   }
}