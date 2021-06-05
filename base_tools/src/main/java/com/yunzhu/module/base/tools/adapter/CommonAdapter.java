package com.yunzhu.module.base.tools.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Common Adapter 
 * @author lisc
 * @date 2015/12/15
 * @version 1.0 适用于ListView GridView的快速适配
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
	
	protected List<T> mData;
	protected final int mItemLayoutId;
	
	public CommonAdapter(int itemLayoutId, List<T> datas){
		this.mItemLayoutId = itemLayoutId;
		this.mData = datas ;
	}
	
	public void refreshView(List<T> data)
	{
		this.mData = data;  
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return mData==null ? 0 :mData.size();
	}

	@Override
	public T getItem(int position) {
		if(mData != null && position< mData.size()){
			return mData.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public abstract void convert(CommonViewHolder holder, T item, int position);

	@Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final CommonViewHolder viewHolder = CommonViewHolder.get(convertView, parent, mItemLayoutId);
        convert(viewHolder, getItem(position),position);  
        return viewHolder.getConvertView();    
    }
	
}
