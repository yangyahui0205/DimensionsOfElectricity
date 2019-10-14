package com.baway.dimensionsofelectricity.ui.fragment;

import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.CircleBean;
import com.baway.dimensionsofelectricity.data.net.HttpUtils;
import com.baway.dimensionsofelectricity.di.contract.ICircleContract;
import com.baway.dimensionsofelectricity.di.presenter.CirclePresenter;
import com.baway.dimensionsofelectricity.ui.adapter.CircleAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static android.content.Context.MODE_PRIVATE;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.16
 *@Time: 14:19:26
 *@Description:
 * */public class CircleFragment extends BaseFragment<ICircleContract.ICircleView, CirclePresenter<ICircleContract.ICircleView>> implements ICircleContract.ICircleView {

    @BindView(R.id.rv_circle)
    RecyclerView rvCircle;
    @BindView(R.id.srl_circle_layout)
    SmartRefreshLayout srlCircleLayout;
    private int page = 1;
    private int userId;
    private String sessionId;

    @Override
    public void showCircleData(String message) {
        Gson gson = new Gson();
        CircleBean qiuBean = gson.fromJson(message, CircleBean.class);
        ArrayList<CircleBean.ResultBean> result = (ArrayList<CircleBean.ResultBean>) qiuBean.getResult();
        //创建适配器
        CircleAdapter adapter = new CircleAdapter(R.layout.layout_item_circle, result);
        rvCircle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvCircle.setAdapter(adapter);
        adapter.setOnClickCircleStatusListener(new CircleAdapter.OnClickCircleStatusListener() {
            @Override
            public void addCircleGreat(int id) {
                HttpUtils
                        .getInstance()
                        .getApiService()
                        .addCircleGreat(userId, sessionId, id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResponseBody>() {
                            @Override
                            public void accept(ResponseBody responseBody) throws Exception {
                                Toast.makeText(mContext, responseBody.string(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            @Override
            public void cancelCircleGreat(int id) {
                HttpUtils
                        .getInstance()
                        .getApiService()
                        .cancelCircleGreat(userId, sessionId, id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResponseBody>() {
                            @Override
                            public void accept(ResponseBody responseBody) throws Exception {
                                Toast.makeText(mContext, responseBody.string(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @Override
    protected CirclePresenter<ICircleContract.ICircleView> createPresenter() {
        return new CirclePresenter<>();
    }

    @Override
    protected void initData() {
        SharedPreferences user = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        userId = user.getInt("userId", 4870);
        sessionId = user.getString("sessionId", "");

        presenter.requestCircleData(userId, sessionId, page);

        srlCircleLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                presenter.requestCircleData(userId, sessionId, page);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        srlCircleLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                page++;
                presenter.requestCircleData(userId, sessionId, page);
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        //设置 Header 为 贝塞尔雷达 样式
        srlCircleLayout.setRefreshHeader(new StoreHouseHeader(mContext));
        //设置 Footer 为 球脉冲 样式
        srlCircleLayout.setRefreshFooter(new ClassicsFooter(mContext));
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_circle;
    }

}
