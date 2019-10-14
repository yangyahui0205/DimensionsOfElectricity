package com.baway.dimensionsofelectricity.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.CircleBean;
import com.baway.dimensionsofelectricity.data.beans.MyCircleBean;
import com.baway.dimensionsofelectricity.data.net.TimeUtil;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public class MyCircleAdapter extends BaseQuickAdapter<MyCircleBean.ResultBean, BaseViewHolder> {
    private boolean check = false;
    public int num ;

    public MyCircleAdapter(int layoutResId, @Nullable List<MyCircleBean.ResultBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(final BaseViewHolder helper, MyCircleBean.ResultBean item) {
        ImageView img_one = helper.getView(R.id.iv_my_circle_img_one);
        ImageView img_two = helper.getView(R.id.iv_my_circle_img_two);

        final ImageView praise = helper.getView(R.id.iv_my_circle_praise);
        num = item.getGreatNum();
        helper.setText(R.id.tv_my_circle_greatNum, num+"");
        helper.setText(R.id.tv_my_circle_content, item.getContent());
        long createTime = item.getCreateTime();
        String dateTime = TimeUtil.getInstance().timeStamp2Date(String.valueOf(createTime), "yyyy-MM-dd HH:mm:ss");
        helper.setText(R.id.tv_my_circle_tiem, dateTime);

        Glide.with(mContext).load(item.getImage()).into(img_one);
        Glide.with(mContext).load(item.getImage()).into(img_two);

        praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check) {
                    ++num;
                    helper.setText(R.id.tv_my_circle_greatNum, num + "");
                    Glide.with(mContext).load(R.mipmap.common_btn_prise_n).into(praise);
                    check = true;
                } else {
                    --num;
                    helper.setText(R.id.tv_my_circle_greatNum, num + "");
                    Glide.with(mContext).load(R.mipmap.common_btn_prise_s).into(praise);
                    check = false;
                }

            }
        });
    }
}
