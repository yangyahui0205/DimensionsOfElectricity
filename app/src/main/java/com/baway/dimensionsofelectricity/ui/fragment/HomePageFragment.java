package com.baway.dimensionsofelectricity.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.BannerBean;
import com.baway.dimensionsofelectricity.data.beans.HomePageBean;
import com.baway.dimensionsofelectricity.data.beans.MessageEvent;
import com.baway.dimensionsofelectricity.di.contract.IHomePageContract;
import com.baway.dimensionsofelectricity.di.presenter.HomePagePresenter;
import com.baway.dimensionsofelectricity.ui.activity.DetailActivity;
import com.baway.dimensionsofelectricity.ui.activity.SearchActivity;
import com.baway.dimensionsofelectricity.ui.adapter.HotAdapter;
import com.baway.dimensionsofelectricity.ui.adapter.LifeAdapter;
import com.baway.dimensionsofelectricity.ui.adapter.StyleAdapter;
import com.baway.dimensionsofelectricity.ui.widget.HomePageSearchBox;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;


/*
 *@Auther:杨雅慧
 *@Date: 19.7.16
 *@Time: 14:04:58
 *@Description:
 * */public class HomePageFragment extends BaseFragment<IHomePageContract.IHomePageView, HomePagePresenter<IHomePageContract.IHomePageView>> implements IHomePageContract.IHomePageView {


    @BindView(R.id.homePage_searchBox)
    HomePageSearchBox homePageSearchBox;
    @BindView(R.id.homePage_xBanner)
    XBanner homePageXBanner;
    @BindView(R.id.recyclerView_hot)
    RecyclerView recyclerViewHot;
    @BindView(R.id.recyclerView_style)
    RecyclerView recyclerViewStyle;
    @BindView(R.id.recyclerView_life)
    RecyclerView recyclerViewLife;
    @BindView(R.id.dl_search)
    DrawerLayout dlSearch;

    @Override
    public void showHomePageXBannerData(String message) {
        Log.d("HomePageFragment", message);
        Gson gson = new Gson();
        final BannerBean bannerBean = gson.fromJson(message, BannerBean.class);
        //加载广告图片
        homePageXBanner.setBannerData(bannerBean.getResult());
        homePageXBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(mContext).load(bannerBean.getResult().get(position).getXBannerUrl()).into((ImageView) view);
            }
        });
    }


    @Override
    public void showHomePageData(String message) {
        Log.d("HomePageFragment", message);
        Gson gson = new Gson();
        HomePageBean pageBean = gson.fromJson(message, HomePageBean.class);
        final List<HomePageBean.ResultBean.MlssBean.CommodityListBeanXX> mlss = pageBean.getResult().getMlss().getCommodityList();
        final List<HomePageBean.ResultBean.PzshBean.CommodityListBeanX> pzsh = pageBean.getResult().getPzsh().getCommodityList();
        final List<HomePageBean.ResultBean.RxxpBean.CommodityListBean> rxxp = pageBean.getResult().getRxxp().getCommodityList();
        recyclerViewHot.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        HotAdapter hotAdapter = new HotAdapter(R.layout.layout_item_hot, rxxp);
        recyclerViewHot.setAdapter(hotAdapter);

        recyclerViewStyle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        StyleAdapter styleAdapter = new StyleAdapter(R.layout.layout_item_style, mlss);
        recyclerViewStyle.setAdapter(styleAdapter);

        recyclerViewLife.setLayoutManager(new GridLayoutManager(getContext(), 2));
        LifeAdapter lifeAdapter = new LifeAdapter(R.layout.layout_item_life, pzsh);
        recyclerViewLife.setAdapter(lifeAdapter);

        hotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MessageEvent event = new MessageEvent();
                event.setCommodityId(rxxp.get(position).getCommodityId());
                EventBus.getDefault().postSticky(event);
                startActivity(new Intent(getContext(), DetailActivity.class));
            }
        });
        styleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MessageEvent event = new MessageEvent();
                event.setCommodityId(mlss.get(position).getCommodityId());
                EventBus.getDefault().postSticky(event);
                startActivity(new Intent(getContext(), DetailActivity.class));
            }
        });
        lifeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MessageEvent event = new MessageEvent();
                event.setCommodityId(pzsh.get(position).getCommodityId());
                EventBus.getDefault().postSticky(event);
                startActivity(new Intent(getContext(), DetailActivity.class));

            }
        });
    }


    @Override
    protected HomePagePresenter<IHomePageContract.IHomePageView> createPresenter() {
        return new HomePagePresenter<>();
    }

    @Override
    protected void initData() {
        presenter.requestHomePageXBannerData();
        presenter.requestHomePageData();
        homePageSearchBox.setOnClickSearchListener(new HomePageSearchBox.OnClickSearchBoxListener() {
            @Override
            public void search() {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }

            @Override
            public void more() {
                dlSearch.openDrawer(Gravity.LEFT);
            }
        });
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_homepage;
    }

}
