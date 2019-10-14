package com.baway.dimensionsofelectricity.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.MessageEvent;
import com.baway.dimensionsofelectricity.data.beans.SearchListBean;
import com.baway.dimensionsofelectricity.di.contract.ISearchListContract;
import com.baway.dimensionsofelectricity.di.presenter.SearchListPresenter;
import com.baway.dimensionsofelectricity.ui.adapter.SearchListAdapter;
import com.baway.dimensionsofelectricity.ui.widget.CustomSearchBox;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchlistingsActivity extends BaseActivity<ISearchListContract.ISearchListView, SearchListPresenter<ISearchListContract.ISearchListView>> implements ISearchListContract.ISearchListView {


    @BindView(R.id.custom_searchListings)
    CustomSearchBox customSearchListings;
    @BindView(R.id.rv_searchContent)
    RecyclerView rvSearchContent;
    @BindView(R.id.srl_layout)
    SmartRefreshLayout srlLayout;
    private String encode;
    private int page = 1;
    private ArrayList<SearchListBean.ResultBean> result;
    private SearchListAdapter adapter;

    @Override
    public void showSearchListData(String message) {
        Log.d("SearchlistingsActivity", message);
        Gson gson = new Gson();
        SearchListBean searchBean = gson.fromJson(message, SearchListBean.class);
        if (searchBean != null) {
            result = (ArrayList<SearchListBean.ResultBean>) searchBean.getResult();
            rvSearchContent.setLayoutManager(new GridLayoutManager(context, 2));
            adapter = new SearchListAdapter(R.layout.layout_item_search, result);
            rvSearchContent.setAdapter(adapter);

        }
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //Toast.makeText(SearchlistingsActivity.this, "" + result.get(position).getCommodityId(), Toast.LENGTH_SHORT).show();
               /* MessageEvent event = new MessageEvent();
                Log.d("SearchlistingsActivity", "result.get(position).getCommodityId():" + result.get(position).getCommodityId());
                event.setCommodityId(136);
                EventBus.getDefault().postSticky(event);*/
                Intent intent = new Intent(SearchlistingsActivity.this, DetailActivity.class);
                intent.putExtra("commodityId", result.get(position).getCommodityId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected SearchListPresenter<ISearchListContract.ISearchListView> createPresenter() {
        return new SearchListPresenter<>();
    }

    @Override
    protected void initData() {
        customSearchListings.setOnClickSearchBoxListener(new CustomSearchBox.OnClickSearchBoxListener() {
            @Override
            public void searchResult(String content) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void search() {
                startActivity(new Intent(SearchlistingsActivity.this, SearchActivity.class));
                finish();
            }

            @Override
            public void moreResult() {

            }
        });

        srlLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                presenter.requestSearchListData(encode, page);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        srlLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                page++;
                presenter.requestSearchListData(encode, page);
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        //设置 Header 为 贝塞尔雷达 样式
        srlLayout.setRefreshHeader(new StoreHouseHeader(context));
        //设置 Footer 为 球脉冲 样式
        srlLayout.setRefreshFooter(new ClassicsFooter(context));
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_searchlistings;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(MessageEvent event) {
        customSearchListings.setContent(event.getContent());
        encode = event.getContent();
        presenter.requestSearchListData(event.getContent(), page);
    }

    ;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
