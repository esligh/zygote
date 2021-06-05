package com.yunzhu.module.base.tools.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder{

    private SparseArray<View> views = new SparseArray<>();
    private Context context;

    public ViewHolder(@NonNull Context context,ViewGroup parent,@LayoutRes int layoutId) {
        super(LayoutInflater.from(context).inflate(layoutId,parent,false));
        this.context = context;
    }

    public <T extends View> T getView(int viewId)
    {
        View view = views.get(viewId);
        if(view == null){
            view = itemView.findViewById(viewId);
            if (view == null) {
                throw new IllegalArgumentException("not found id");
            }
            views.put(viewId, view);
        }
        return (T) view ;
    }


    public ViewHolder setText(@IdRes int viewId, CharSequence text){
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    public ViewHolder setText(@IdRes int viewId, @StringRes int resId){
        TextView textView = getView(viewId);
        textView.setText(context.getString(resId));
        return this;
    }

    public ViewHolder setTextColor(@IdRes int viewId, @ColorRes int colorRes)
    {
        TextView textView = getView(viewId);
        textView.setTextColor(ContextCompat.getColor(context,colorRes));
        return this;
    }

    public ViewHolder setImageResource(@IdRes int viewId, @DrawableRes int resId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    public ViewHolder gone(@IdRes int viewId){
        View view = getView(viewId);
        view.setVisibility(View.GONE);
        return this;
    }

    public ViewHolder gone(View view){
        view.setVisibility(View.GONE);
        return this;
    }

    public ViewHolder visible(@IdRes int viewId){
        View view = getView(viewId);
        view.setVisibility(View.VISIBLE);
        return this;
    }

    public ViewHolder visible(View view){
        view.setVisibility(View.VISIBLE);
        return this;
    }

}