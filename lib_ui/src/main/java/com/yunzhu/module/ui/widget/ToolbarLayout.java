package com.yunzhu.module.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.yunzhu.module.ui.R;


public class ToolbarLayout extends FrameLayout {

    private Context context;
    private ImageView toolbarBack;
    private TextView toolbarTitle;
    private View toolbarLine;

    public ToolbarLayout(@NonNull Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    public ToolbarLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View rootView = View.inflate(context, R.layout.frame_toolbar,this);
        toolbarBack = rootView.findViewById(R.id.toolbarBack);
        toolbarTitle= rootView.findViewById(R.id.toolbarTitle);
        toolbarLine = rootView.findViewById(R.id.toolbarLine);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.ToolbarLayout);
        String title = typedArray.getString(R.styleable.ToolbarLayout_title);
        boolean showBack = typedArray.getBoolean(R.styleable.ToolbarLayout_showBack,true);
        boolean showLine = typedArray.getBoolean(R.styleable.ToolbarLayout_showLine,true);
        int textColor = typedArray.getColor(R.styleable.ToolbarLayout_textColor, ContextCompat.getColor(context,R.color.frame_toolbar_text_color));
        int iconTintColor = typedArray.getColor(R.styleable.ToolbarLayout_textColor, ContextCompat.getColor(context,R.color.frame_toolbar_text_color));
        typedArray.recycle();

        toolbarTitle.setText(title);
        toolbarTitle.setTextColor(textColor);
        tintIcon(toolbarBack,iconTintColor);
        if(showBack) {
            toolbarBack.setVisibility(View.VISIBLE);
        }else{
            toolbarBack.setVisibility(View.GONE);
        }

        if(showLine){
            toolbarLine.setVisibility(View.VISIBLE);
        }else{
            toolbarLine.setVisibility(View.GONE);
        }

        //默认toolbar背景白色
        setBackgroundColor(ContextCompat.getColor(context,R.color.frame_toolbar_bg_color));

    }

    public void setTitle(CharSequence title)
    {
        toolbarTitle.setText(title);
    }

    public void setTextColor(@ColorRes int resId)
    {
        toolbarTitle.setTextColor(ContextCompat.getColor(context,resId));
    }

    public void setToolbarBackgroundColor(@ColorRes int resId)
    {
        setBackgroundColor(ContextCompat.getColor(context,resId));
    }

    public void showBack(boolean b)
    {
        if(b) {
            toolbarBack.setVisibility(View.VISIBLE);
        }else{
            toolbarBack.setVisibility(View.GONE);
        }
    }

    public void showLine(boolean b)
    {
        if(b){
            toolbarLine.setVisibility(View.VISIBLE);
        }else{
            toolbarLine.setVisibility(View.GONE);
        }
    }

    private void tintIcon(ImageView imageView , int colors) {
        Drawable wrappedDrawable = DrawableCompat.wrap(imageView.getDrawable());
        DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(colors));
        imageView.setImageDrawable(wrappedDrawable);
    }

    public void setBackClickListener(final OnBackClickListener l)
    {
        toolbarBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if( l != null){
                    l.onBackClick();
                }
            }
        });
    }


    public interface OnBackClickListener
    {
        void onBackClick();
    }


}