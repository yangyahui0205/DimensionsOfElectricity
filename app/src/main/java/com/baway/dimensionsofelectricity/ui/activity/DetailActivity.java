package com.baway.dimensionsofelectricity.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.DetailsBean;
import com.baway.dimensionsofelectricity.data.beans.MessageEvent;
import com.baway.dimensionsofelectricity.di.contract.IDetailContract;
import com.baway.dimensionsofelectricity.di.presenter.DetailPresenter;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.ResponseBody;

public class DetailActivity extends BaseActivity<IDetailContract.IDetailView, DetailPresenter<IDetailContract.IDetailView>> implements IDetailContract.IDetailView {


    @BindView(R.id.iv_detail_back)
    ImageView ivDetailBack;
    @BindView(R.id.banner_detail_picture)
    XBanner bannerDetailPicture;
    @BindView(R.id.tv_detail_price)
    TextView tvDetailPrice;
    @BindView(R.id.tv_detail_sold)
    TextView tvDetailSold;
    @BindView(R.id.tv_detail_name)
    TextView tvDetailName;
    @BindView(R.id.tv_detail_describe)
    TextView tvDetailDescribe;
    @BindView(R.id.tv_detail_stroke)
    TextView tvDetailStroke;
    @BindView(R.id.wv_detail_details)
    WebView wvDetailDetails;
    @BindView(R.id.iv_detail_add)
    ImageView ivDetailAdd;
    @BindView(R.id.iv_detail_buy)
    ImageView ivDetailBuy;
    private DetailsBean.ResultBean result;
    private int userId;
    private String sessionId;

    @Override
    public void showDetailData(String message) {
        // categoryId : 1001007004
        //         * categoryName : 彩妆
        //         * commentNum : 32
        //         * commodityId : 8
        //         * commodityName : Lara style蜜颊润泽腮红
        //         * describe : 两色搭配 特有细致控光粉体处理技术与高透明感粉体结合，粉质细腻，色泽柔和，光泽通透，仿佛自带柔焦，打造3D元气小脸不出错
        //         * picture : http://172.17.8.100/images/small/commodity/mzhf/cz/6/1.jpg,http://172.17.8.100/images/small/commodity/mzhf/cz/6/2.jpg,http://172.17.8.100/images/small/commodity/mzhf/cz/6/3.jpg,http://172.17.8.100/images/small/commodity/mzhf/cz/6/4.jpg
        //         * * price : 19
        //         * * saleNum : 0
        //         * * stock : 9999
        //         * * weight : 1
        Gson gson = new Gson();
        DetailsBean detailsBean;
        detailsBean = gson.fromJson(message, DetailsBean.class);
        result = detailsBean.getResult();
        Log.d("DetailActivity", "result:" + result);
        //picture
        tvDetailName.setText(result.getCommodityName());
        tvDetailPrice.setText(result.getPrice() + "");
        tvDetailStroke.setText(result.getStock() + "");
        tvDetailDescribe.setText(result.getDescribe());
        String picture = result.getPicture();
        final String[] split = picture.split(",");
        final List<String> resultList = new ArrayList<>(Arrays.asList(split));
        bannerDetailPicture.setData(resultList, null);
        bannerDetailPicture.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(DetailActivity.this).load(resultList.get(position)).into((ImageView) view);
                banner.setPageChangeDuration(3000);
            }
        });
        wvDetailDetails.loadData("<html><title></title><body>" + result.getDetails() + "</body></html>", "text/html", "utf-8");
        wvDetailDetails.setWebChromeClient(new WebChromeClient());
    }

    @Override
    public void showSyncShoppingCartResult(String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getMessage(MessageEvent msg) {
        int commodityId = msg.getCommodityId();
        Log.d("DetailActivity", "commodityId:" + commodityId);
        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        userId = user.getInt("userId", 4870);
        sessionId = user.getString("sessionId", "");
        if (commodityId > 0) {
            presenter.requestDetailData(userId, sessionId, commodityId);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected DetailPresenter<IDetailContract.IDetailView> createPresenter() {
        return new DetailPresenter<>();
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        int commodityId = intent.getIntExtra("commodityId", 0);
        Log.d("DetailActivity", "initData:commodityId:" + commodityId);
        if (commodityId > 0) {
            presenter.requestDetailData(userId, sessionId, commodityId);
        }
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_detail;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.iv_detail_back)
    public void onViewClicked() {
    }

    @OnClick({R.id.iv_detail_back, R.id.iv_detail_add, R.id.iv_detail_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_detail_back:
                //返回页面
                finish();
                break;
            case R.id.iv_detail_add:
                //添加购物车
                // Gson转换
                Gson gson = new Gson();
                //定义Json格式的结构
                HashMap<String, Integer> paramsMap = new HashMap<>();
                paramsMap.put("count", 1);
                paramsMap.put("commodityId", result.getCommodityId());
                String json = gson.toJson(paramsMap);
                Log.d("DetailActivity", "orderInfo:[" + json + "]");

                FormBody formBody = new FormBody
                        .Builder()
                        // .add("data", "[{\"commodityId\":" + result.getCommodityId() + ",\"count\":1}]")
                        .add("data", "[" + json + "]")
                        .build();
                presenter.syncShoppingCart(userId, sessionId, formBody);
                break;
            case R.id.iv_detail_buy:
                //T0DO 购买商品
                break;
        }
    }
}
