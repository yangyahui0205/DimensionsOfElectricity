package com.baway.dimensionsofelectricity.di.presenter;

import java.lang.ref.WeakReference;

/*
 *@Auther:杨雅慧
 *@Date: 19.6.21
 *@Time: 17:24:19
 *@Description:
 * */public class BasePresenter<V> {

    private WeakReference<V> weakReference;

    //关联V层
    public void attachView(V v) {
        //弱引用
        weakReference = new WeakReference<>(v);
    }

    //获取V层
    public V getView() {
        return weakReference.get();
    }

    //解绑
    public void detachView() {
        weakReference.clear();
    }
}
