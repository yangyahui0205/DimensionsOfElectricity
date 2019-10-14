package com.baway.dimensionsofelectricity.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.WalletBean;
import com.baway.dimensionsofelectricity.data.net.HttpUtils;
import com.baway.dimensionsofelectricity.ui.adapter.MyWalletAdapter;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MineWalletActivity extends AppCompatActivity {

    @BindView(R.id.tv_wallet_balance)
    TextView tvWalletBalance;
    @BindView(R.id.rv_wallet)
    RecyclerView rvWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_wallet);
        ButterKnife.bind(this);
        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        int userId = user.getInt("userId", 4870);
        String sessionId = user.getString("sessionId", "");
        HttpUtils
                .getInstance()
                .getApiService()
                .findUserWallet(userId, sessionId, 1, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String responseBodyString = responseBody.string();
                        Log.d("MineWalletActivity", responseBodyString);
                        Gson gson = new Gson();
                        WalletBean walletBean = gson.fromJson(responseBodyString, WalletBean.class);
                        WalletBean.ResultBean result = walletBean.getResult();
                        tvWalletBalance.setText(result.getBalance()+"");
                        rvWallet.setLayoutManager(new LinearLayoutManager(MineWalletActivity.this, LinearLayoutManager.VERTICAL, false));

                        MyWalletAdapter adapter = new MyWalletAdapter(R.layout.layout_item_wallet, result.getDetailList());
                        rvWallet.setAdapter(adapter);
                    }
                });
    }
}
