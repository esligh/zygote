package com.yunzhu.module.common.datasource;

import androidx.paging.PageKeyedDataSource;
import androidx.annotation.NonNull;

/**
 * @author:lisc
 * @date:2019-10-14
 */

public class PagedDataSource<T> extends PageKeyedDataSource<Integer,T> {
    private PagedDataLoader<T> dataLoader;

    public PagedDataSource(PagedDataLoader<T> dataLoader){
        this.dataLoader = dataLoader;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, T> callback) {
        dataLoader.loadInitial(params, callback);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, T> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, T> callback) {
        dataLoader.loadAfter(params,callback);
    }
}
