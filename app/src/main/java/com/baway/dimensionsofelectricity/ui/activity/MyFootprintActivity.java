package com.baway.dimensionsofelectricity.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.MyCircleBean;
import com.baway.dimensionsofelectricity.data.beans.MyFootPrintBean;
import com.baway.dimensionsofelectricity.data.net.HttpUtils;
import com.baway.dimensionsofelectricity.ui.adapter.MyCircleAdapter;
import com.baway.dimensionsofelectricity.ui.adapter.MyFootPrindAdapter;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MyFootprintActivity extends AppCompatActivity {

    @BindView(R.id.rv_my_footPrint)
    RecyclerView rvMyFootPrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_footprint);
        ButterKnife.bind(this);
        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        int userId = user.getInt("userId", 4870);
        String sessionId = user.getString("sessionId", "");
        HttpUtils
                .getInstance()
                .getApiService()
                .browseList(userId, sessionId, 1, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String responseBodyString = responseBody.string();
                        Gson gson = new Gson();
                        MyFootPrintBean myFootPrintBean = gson.fromJson(responseBodyString, MyFootPrintBean.class);
                        List<MyFootPrintBean.ResultBean> result = myFootPrintBean.getResult();

                        rvMyFootPrint.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

                        MyFootPrindAdapter adapter = new MyFootPrindAdapter(R.layout.layout_item_my_footprint, result);
                        rvMyFootPrint.setAdapter(adapter);
                    }
                });
    }
}
