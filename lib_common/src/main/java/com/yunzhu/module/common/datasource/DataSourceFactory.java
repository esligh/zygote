package com.yunzhu.module.common.datasource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

/**
 * @author:lisc
 * @date:2019-10-14
 */

public class DataSourceFactory<T> extends DataSource.Factory<Integer,T>{

    private MutableLiveData<PagedDataSource<T>> sourceLiveData = new MutableLiveData<>();
    private PagedDataLoader<T> dataLoader;

    public DataSourceFactory(PagedDataLoader<T> dataLoader)
    {
        this.dataLoader = dataLoader;
    }

    @Override
    public PagedDataSource<T> create() {
        PagedDataSource dataSource = new PagedDataSource(dataLoader);
        sourceLiveData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<PagedDataSource<T>> getSourceLiveData()
    {
        return sourceLiveData;
    }
}
