package com.yunzhu.module.bus.constant.page

/**
 * @author:lisc
 * @date: 2019-06-10
 * @description: 定义各组件的页面标识，组件的开发者需要在此定义自身组件页面标识
 * 《注：不同模块须使用不同一级路径,否则无法跳转》
 * Page 包含了各组件的实例引用，实例名称以大驼峰命名方式
 */
object Page
{
    val AppModule = AppPage

    val MainModule = MainPage

    val HomeModule = HomePage

    val CommonModule = CommonPage
}