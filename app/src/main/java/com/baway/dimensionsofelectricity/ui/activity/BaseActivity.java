package com.baway.dimensionsofelectricity.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baway.dimensionsofelectricity.di.presenter.BasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
 *@Auther:杨雅慧
 *@Date: 19.6.21
 *@Time: 17:28:11
 *@Description:
 * */public abstract class BaseActivity<V, P extends BasePresenter<V>> extends AppCompatActivity {

    public P presenter;
    public static Context context;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //视图绑定
        setContentView(bindLayout());
        //ButterKnife绑定
        unbinder = ButterKnife.bind(this);
        //获取P层
        presenter = createPresenter();
        //进行V层的绑定
        presenter.attachView((V) this);
        //定义上下文
        context = this;
        //初始化数据
        initData();
    }

    protected abstract P createPresenter();

    protected abstract void initData();

    protected abstract int bindLayout();
    //和V层解绑

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter.detachView();

    }
}
