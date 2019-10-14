package com.baway.dimensionsofelectricity.di.presenter;

import android.util.Log;

import com.baway.dimensionsofelectricity.di.contract.ISearchListContract;
import com.baway.dimensionsofelectricity.di.model.SearchListModel;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.17
 *@Time: 16:15:08
 *@Description:
 * */public class SearchListPresenter<V extends ISearchListContract.ISearchListView> extends BasePresenter<V> {

    private final SearchListModel model;

    public SearchListPresenter() {
        model = new SearchListModel();
    }

    public void requestSearchListData(String content, int page) {
        Log.d("SearchListPresenter", content+page);
        model.containSearchListData(content, page, new ISearchListContract.ISearchListModel.CallBack() {
            @Override
            public void getSearchListResult(String message) {
                getView().showSearchListData(message);
            }
        });
    }
}
