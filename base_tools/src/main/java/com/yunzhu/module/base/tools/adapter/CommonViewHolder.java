package com.yunzhu.module.base.tools.adapter;


import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author lisc
 * @date 2015/12/15
 * @version 1.0
 */
public class CommonViewHolder {
	
	private final SparseArray<View> mViews;
	private View mConvertView;
	private ViewGroup mParent;
	
	public CommonViewHolder(View rootView)
	{		
		this.mConvertView = rootView; 
		this.mViews = new SparseArray<View>();
	}
	
	/**
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @return
	 */
	public static CommonViewHolder get(View convertView,
                                 ViewGroup parent, int layoutId)
	{
		if(convertView == null){
			return new CommonViewHolder(parent, layoutId);
		}
		return (CommonViewHolder)convertView.getTag();
	}
		
	/**
	 * @param parent
	 * @param layoutId
	 */
	private CommonViewHolder(ViewGroup parent, int layoutId)
	{
		
		this.mParent = parent ; 
		this.mViews = new SparseArray<View>();
		this.mConvertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent,false);
		this.mConvertView.setTag(this);
	}
	
	@SuppressWarnings("unchecked")
	public  <T extends View> T getView(int viewId)
	{
		View view = mViews.get(viewId);
		if(view == null){
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view ; 
	}
	
	public View getConvertView()
	{
		return mConvertView; 
	}
	
	public ViewGroup getParent()
	{
		return mParent ;
	}
	
    
	/**if listview
	 * @param viewId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends ListView> T getListView(int viewId)
	{
		return (T)getView(viewId);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends TextView> T getTextView(int viewId)
	{
		return (T)getView(viewId);
	}

	public <T extends ImageView> T getImageView(int viewId)
	{
		return (T)getView(viewId);
	}
	
	public void setVisibility(int viewId,int visibility)
	{
		getView(viewId).setVisibility(visibility);
	}
	
	public void setChecked(int viewId,boolean flag)
	{
		((CheckBox)getView(viewId)).setChecked(flag);
	}
	
	/**
	 * @param viewId
	 * @param text
	 * @return
	 */
	public CommonViewHolder setTvText(int viewId, String text){
		TextView view = getView(viewId);
		view.setText(text);
		return this; 
	}
	
	public void setTvTextColor(int viewId,int color)
	{
		TextView view = getView(viewId);
		view.setTextColor(color);
	}
	
	/**
	 * @param viewId
	 * @param text
	 * @return
	 */
	public CommonViewHolder setEtText(int viewId, String text)
	{
		EditText view = getView(viewId);
		view.setText(text);
		return this;
	}

	/**
     * @param viewId 
     * @param drawableId 
     * @return 
     */  
    public CommonViewHolder setImageResource(int viewId, int drawableId)
    {  
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);    
        return this;  
    } 
    
    
    public void setTextColor(int viewId,int color)
    {
    	TextView tv = getView(viewId);
    	tv.setTextColor(color);
    }
    
    public void setTextSize(int viewId,float size)
    {
    	TextView tv = getView(viewId);
    	tv.setTextSize(size);
    }

	public void setTvVisibility(int viewId,int visibleStatus)
	{
		TextView tv = getView(viewId);
		tv.setVisibility(visibleStatus);
	}
    
    /**
     * @param viewId
     * @return 
     */  
    public CommonViewHolder setImageBitmap(int viewId, Bitmap bm)
    {  
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);  
        return this;  
    }  
    
     
    
}
