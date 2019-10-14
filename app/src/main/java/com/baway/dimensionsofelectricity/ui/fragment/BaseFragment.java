package com.baway.dimensionsofelectricity.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baway.dimensionsofelectricity.di.presenter.BasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
 *@Auther:杨雅慧
 *@Date: 19.6.26
 *@Time: 17:23:17
 *@Description:
 * */public abstract class BaseFragment<V, P extends BasePresenter<V>> extends Fragment {

    public P presenter;
    public Context mContext;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(bindLayout(), container, false);
        unbinder = ButterKnife.bind(this,view);
        mContext = getContext();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter = createPresenter();

        presenter.attachView((V) this);
        initData();
    }

    protected abstract P createPresenter();

    protected abstract void initData();

    protected abstract int bindLayout();

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        unbinder.unbind();
    }
}
