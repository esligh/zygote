package com.yunzhu.module.common.datasource;

import androidx.paging.PageKeyedDataSource;

/**
 * @author:lisc
 * @date:2019-10-14
 */

public interface PagedDataLoader<T>{
    void loadInitial(PageKeyedDataSource.LoadInitialParams<Integer> params, PageKeyedDataSource.LoadInitialCallback<Integer, T> callback);
    void loadAfter(PageKeyedDataSource.LoadParams<Integer> params, PageKeyedDataSource.LoadCallback<Integer, T> callback);
    void refresh();
    void loadMore();
}
