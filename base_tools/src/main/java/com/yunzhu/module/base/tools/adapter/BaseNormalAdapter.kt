package com.yunzhu.module.base.tools.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
/**
 * 通用的RecyclerView的适配器,支持多布局
 * 对于单布局的适配器对象，在构造时指定layoutId即可
 * 对于多布局方式，需要根据元素类型重载getItemLayout方法
 * */
abstract class BaseNormalAdapter<T>(private val layoutId: Int = 0, var list: ArrayList<T> = ArrayList()) :
    androidx.recyclerview.widget.RecyclerView.Adapter<ViewHolder>() {

    lateinit var context: Context

    var itemClickListener: ((View, T, Int) -> Unit)? = null
    var itemLongClickListener: ((View, T, Int) -> Boolean)? = null

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return if(layoutId > 0 ) {
            ViewHolder(context, parent, layoutId)
        }else{
            ViewHolder(context, parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        itemClickListener?.let { holder.itemView.setOnClickListener { it(holder.itemView, list[position], position) } }
        bindViewHolder(holder, list[position], position)
    }

    override fun getItemViewType(position: Int): Int {
        val data: T = list[position]
        return getItemLayout(data, position)
    }

    open fun getItemLayout(data: T?, position: Int): Int {
        return layoutId
    }

    fun setData(data: ArrayList<T>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun addData(data: ArrayList<T>) {
        if (data.isEmpty()) {
            return
        }

        val size = list.size
        list.addAll(data)
        notifyItemRangeInserted(size, data.size)
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItem(data: T) {
        val size = list.size
        list.add(data)
        notifyItemInserted(size)
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    abstract fun bindViewHolder(holder: ViewHolder, item: T, position: Int)
}