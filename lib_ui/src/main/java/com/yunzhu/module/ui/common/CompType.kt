package com.yunzhu.module.ui.common

/**

 * @author: lisc
 * @date: 2019-12-25 下午 04:18
 * @desc: 组件类型定义所有可识别的组件在此定义
 */
object CompType
{
    //公共组件类型
    object Common
    {
        const val ONE_COLUMN = "container-oneColumn"
        const val TWO_COLUMN = "container-twoColumn"
        const val THREE_COLUMN = "container-threeColumn"
    }

    object CardTrade
    {
        const val HOME_CARD = "home_card"
        const val QR_CODE = "qr_code"

    }

    object Home
    {
        const val TYPE_UMI_CATEGORY = "type_umi_category"
        const val TYPE_UMI_GOODS = "type_umi_goods"
        const val TYPE_UMI_HEAD_GOODS = "type_umi_head_goods"
    }

    object Card
    {
        const val HOME_CARD_ADD= "home_card_add"
        const val HOME_CARD_BAG= "home_card_bag"

    }


}