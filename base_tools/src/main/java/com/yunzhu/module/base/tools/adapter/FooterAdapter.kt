package com.yunzhu.module.base.tools.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yunzhu.module.base.tools.R

/**
 * Paging adapter 的footer
 * */
class FooterAdapter(private val retry: () -> Unit) : LoadStateAdapter<FooterAdapter.FooterViewHolder>() {
    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        holder.progressBar.isVisible = loadState is LoadState.Loading
        holder.btnRetry.isVisible = loadState is LoadState.Error
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.module_base_tools_paging_footer_item, parent, false)
        val holder =  FooterViewHolder(view)
        //加载失败时，点击重新请求
        holder.btnRetry.setOnClickListener {
            retry()
        }
        return holder
    }

    class FooterViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        var progressBar: ProgressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
        var btnRetry:TextView = view.findViewById<TextView>(R.id.bt_retry)
    }
}