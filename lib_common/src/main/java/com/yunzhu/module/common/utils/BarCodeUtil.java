package com.yunzhu.module.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import com.google.zxing.oned.Code128Writer;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;

import static cn.bingoogolapple.qrcode.zxing.QRCodeEncoder.syncEncodeBarcode;

/**
 * @author:jiyc
 * @date:2020/4/30
 * 条码宽度调整：去掉Zxing生成条码左右两边padding
 * https://blog.csdn.net/hss01248/article/details/81214600
 */
public class BarCodeUtil {

    /** @param context 尽量用activity,以防使用过屏幕适配工具类后application context 和activity里的desplaymetric里的dpidensity不一致
     *@param expectWidth 期望的宽度
     *@param maxWidth 最大允许宽度
     * @param contents 生成条形码的内容
     * @param height
     * @return
     */
    public static Bitmap getBarCodeWithoutPadding(Context context, int expectWidth, int maxWidth, int height, String contents){
        int width = BGAQRCodeUtil.dp2px(context, expectWidth);
        int widthMax = BGAQRCodeUtil.dp2px(context, maxWidth);
        int heightExpect = BGAQRCodeUtil.dp2px(context, height);
        int realWidth = getBarCodeNoPaddingWidth(width,contents,widthMax);

        return syncEncodeBarcode(contents, realWidth, heightExpect,0);
    }

    private static int getBarCodeNoPaddingWidth(int expectWidth,String contents,int maxWidth){
        boolean[] code = new Code128Writer(). encode(contents);
        int inputWidth = code.length;
        //code:210000000000000082 code.length:134 expectWidth:397 maxWidth:435
        // Add quiet zone on both sides.
        //int fullWidth = inputWidth + 0;
        double outputWidth = (double) Math.max(expectWidth, inputWidth);
        double multiple = outputWidth / inputWidth;
        //multiple:2.962686567164179
        //优先取大的
        int returnVal =0;
        int ceil = (int) Math.ceil(multiple);
        if(inputWidth * ceil <= maxWidth){
            returnVal =  inputWidth * ceil;
        }else {
            int floor = (int) Math.floor(multiple);
            returnVal =  inputWidth * floor;
        }
        return returnVal;
    }

}
