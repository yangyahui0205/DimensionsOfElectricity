package com.baway.dimensionsofelectricity.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.MyCircleBean;
import com.baway.dimensionsofelectricity.data.net.HttpUtils;
import com.baway.dimensionsofelectricity.ui.adapter.MyCircleAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MyCircleActivity extends AppCompatActivity {

    @BindView(R.id.rv_my_circle)
    RecyclerView rvMyCircle;
    @BindView(R.id.iv_delete_my_circle)
    ImageView ivDeleteMyCircle;
    private List<MyCircleBean.ResultBean> result;
    private MyCircleAdapter adapter;
    private int userId;
    private String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_circle);
        ButterKnife.bind(this);
        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        userId = user.getInt("userId", 4870);
        sessionId = user.getString("sessionId", "");
        HttpUtils
                .getInstance()
                .getApiService()
                .findMyCircleById(userId, sessionId, 1, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        findMyCircleById(responseBody);
                    }
                });
    }

    private void findMyCircleById(ResponseBody responseBody) throws IOException {
        String responseBodyString = responseBody.string();
        Gson gson = new Gson();
        MyCircleBean myCircleBean = gson.fromJson(responseBodyString, MyCircleBean.class);
        result = myCircleBean.getResult();

        rvMyCircle.setLayoutManager(new LinearLayoutManager(MyCircleActivity.this, LinearLayoutManager.VERTICAL, false));

        adapter = new MyCircleAdapter(R.layout.layout_item_my_circle, result);
        rvMyCircle.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpUtils
                .getInstance()
                .getApiService()
                .findMyCircleById(userId, sessionId, 1, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        findMyCircleById(responseBody);
                    }
                });
    }

    @OnClick(R.id.iv_delete_my_circle)
    public void onViewClicked() {
        if (result.size() < 0) {
            Toast.makeText(this, "快去发布圈子吧", Toast.LENGTH_SHORT).show();
        } else {
            HttpUtils
                    .getInstance()
                    .getApiService()
                    .deleteCircle(userId, sessionId, result.get(0).getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResponseBody>() {
                        @Override
                        public void accept(ResponseBody responseBody) throws Exception {
                            String responseBodyString = responseBody.string();
                            Toast.makeText(MyCircleActivity.this, responseBodyString, Toast.LENGTH_SHORT).show();
                            onResume();
                        }
                    });
        }
    }
}
