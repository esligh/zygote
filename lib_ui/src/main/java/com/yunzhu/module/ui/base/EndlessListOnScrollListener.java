package com.yunzhu.module.ui.base;

import android.widget.AbsListView;
import android.widget.ListView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_SETTLING;

public abstract class EndlessListOnScrollListener implements AbsListView.OnScrollListener {

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if((scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) &&
                view.getLastVisiblePosition() == (view.getCount() - 1)) {
            onLoadMore();
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
    }

    /**
     * 加载更多回调
     */
    public abstract void onLoadMore();
}