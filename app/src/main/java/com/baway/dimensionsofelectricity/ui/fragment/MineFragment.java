package com.baway.dimensionsofelectricity.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.UserBean;
import com.baway.dimensionsofelectricity.di.contract.IMineContract;
import com.baway.dimensionsofelectricity.di.presenter.MinePresenter;
import com.baway.dimensionsofelectricity.ui.activity.MineWalletActivity;
import com.baway.dimensionsofelectricity.ui.activity.MyCircleActivity;
import com.baway.dimensionsofelectricity.ui.activity.MyFootprintActivity;
import com.baway.dimensionsofelectricity.ui.activity.MyharvestAddressActivity;
import com.baway.dimensionsofelectricity.ui.activity.PersonalDataActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.16
 *@Time: 14:19:26
 *@Description:
 * */public class MineFragment extends BaseFragment<IMineContract.IMineView, MinePresenter<IMineContract.IMineView>> implements IMineContract.IMineView {

    @BindView(R.id.iv_mine_pic)
    ImageView ivMinePic;
    @BindView(R.id.tv_mine_name)
    TextView tvMineName;
    private UserBean.ResultBean result;
    private int userId;
    private String sessionId;

    @Override
    public void showMineData(String message) {
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(message, UserBean.class);
        result = userBean.getResult();
        String headPic = result.getHeadPic();
        Glide.with(mContext).load(headPic).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivMinePic);
        String nickName = result.getNickName();
        tvMineName.setText(nickName);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.requestMinePresenter(userId,sessionId);
    }

    @Override
    protected MinePresenter<IMineContract.IMineView> createPresenter() {
        return new MinePresenter<>();
    }

    @Override
    protected void initData() {
        SharedPreferences user = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        userId = user.getInt("userId", 4870);
        sessionId = user.getString("sessionId", "");
        presenter.requestMinePresenter(userId, sessionId);
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_mine;
    }

    @OnClick({R.id.ll_my_circle, R.id.ll_my_footprint, R.id.ll_my_wallet, R.id.ll_my_harvest_address, R.id.ll_personal_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //我的圈子
            case R.id.ll_my_circle:
                startActivity(new Intent(getContext(), MyCircleActivity.class));
                break;
            //我的足迹
            case R.id.ll_my_footprint:
                startActivity(new Intent(getContext(), MyFootprintActivity.class));
                break;
            //我的钱包
            case R.id.ll_my_wallet:
                startActivity(new Intent(getContext(), MineWalletActivity.class));
                break;
            //我的收货地址
            case R.id.ll_my_harvest_address:
                startActivity(new Intent(getContext(), MyharvestAddressActivity.class));
                break;
            //我的资料
            case R.id.ll_personal_data:
                Intent intent = new Intent(getContext(), PersonalDataActivity.class);
                intent.putExtra("name",result.getNickName());
                intent.putExtra("pwd",result.getPassword());
                intent.putExtra("pic",result.getHeadPic());
                startActivity(intent);
                break;
        }
    }
}
