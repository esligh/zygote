package com.yunzhu.module.main.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.yunzhu.module.base.tools.adapter.BaseNormalAdapter
import com.yunzhu.module.base.tools.adapter.ViewHolder
import com.yunzhu.module.bus.constant.page.Page
import com.yunzhu.module.main.R
import kotlinx.android.synthetic.main.main_activity_main_behavior.*
import java.util.*

@Route(path = Page.MainModule.BEHAVIOR)
class MainBehaviorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_main_behavior)
        rv.layoutManager = LinearLayoutManager(this)
        val demoData: ArrayList<String> = ArrayList()
        for (i in 0..49) {
            demoData.add("Android -- $i")
        }
        rv.adapter = object : BaseNormalAdapter<String>(R.layout.main_layout_rv_item,demoData) {
            override fun bindViewHolder(holder: ViewHolder, item: String, position: Int) {
                holder.setText(R.id.txt_item,item)
            }
        }
    }
}