package com.yunzhu.module.base.tools.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

/**
 * @author:lisc
 * @date:2019-10-17
 */
public abstract class BasePagedAdapter<T> extends PagedListAdapter<T, ViewHolder>{

    private Context context;
    private OnItemClickListener itemClickListener;

    private int layoutId;

    protected BasePagedAdapter(@LayoutRes int layoutId,@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
        this.layoutId = layoutId;
    }

    public void setOnItemClickListener(OnItemClickListener l)
    {
        this.itemClickListener = l;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        return new ViewHolder(context,viewGroup, layoutId);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        bindViewHolder(viewHolder, getItem(i),i);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if(itemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                itemClickListener.itemClick(holder.itemView,getItem(position),position);
                }
            });
        }
        bindViewHolder(holder, getItem(position), position, payloads);
    }

    public void bindViewHolder(ViewHolder holder, T item, int position, List<Object> payloads ) {
        bindViewHolder(holder, item, position);
    }

    public abstract void bindViewHolder(ViewHolder holder, T item, int position);

    public void  changeItem(int position) {
        for(int i=0;i<getItemCount();i++){
            notifyItemChanged(position);
        }
    }

    public void changeItem(int position, Object payload) {
        for(int i=0;i<getItemCount();i++){
            notifyItemChanged(position, payload);
        }
    }

    public interface OnItemClickListener<T>
    {
        void itemClick(View view, T t, int pos);
    }


    public String getString(@StringRes int resId)
    {
        return context.getString(resId);
    }

}
