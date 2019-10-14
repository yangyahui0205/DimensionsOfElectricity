package com.baway.dimensionsofelectricity.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.CircleBean;
import com.baway.dimensionsofelectricity.data.net.TimeUtil;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public class CircleAdapter extends BaseQuickAdapter<CircleBean.ResultBean, BaseViewHolder> {
    private boolean check = false;
    public int num;

    public CircleAdapter(int layoutResId, @Nullable List<CircleBean.ResultBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final CircleBean.ResultBean item) {
        ImageView img_one = helper.getView(R.id.iv_circle_img_one);
        ImageView img_two = helper.getView(R.id.iv_circle_img_two);
        ImageView pic = helper.getView(R.id.iv_circle_pic);
        final ImageView praise = helper.getView(R.id.iv_circle_praise);
        num = item.getGreatNum();
        helper.setText(R.id.tv_circle_name, item.getNickName());
        helper.setText(R.id.tv_circle_greatNum, item.getGreatNum() + "");
        helper.setText(R.id.tv_circle_content, item.getContent());
        long createTime = item.getCreateTime();
        String dateTime = TimeUtil.getInstance().timeStamp2Date(String.valueOf(createTime), "yyyy-MM-dd HH:mm:ss");
        helper.setText(R.id.tv_circle_tiem, dateTime);
        TextView time = helper.getView(R.id.tv_circle_tiem);

        Glide.with(mContext).load(item.getHeadPic()).into(pic);
        Glide.with(mContext).load(item.getImage()).into(img_one);
        Glide.with(mContext).load(item.getImage()).into(img_two);

        praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check) {
                    ++num;
                    helper.setText(R.id.tv_circle_greatNum, num + "");
                    Glide.with(mContext).load(R.mipmap.common_btn_prise_n).into(praise);
                    check = true;
                    onClickCircleStatusListener.addCircleGreat(item.getId());
                } else {
                    --num;
                    helper.setText(R.id.tv_circle_greatNum, num + "");
                    Glide.with(mContext).load(R.mipmap.common_btn_prise_s).into(praise);
                    check = false;
                    onClickCircleStatusListener.cancelCircleGreat(item.getId());
                }

            }
        });
    }

    public interface OnClickCircleStatusListener {
        public void addCircleGreat(int id);

        public void cancelCircleGreat(int id);
    }

    OnClickCircleStatusListener onClickCircleStatusListener;

    public void setOnClickCircleStatusListener(OnClickCircleStatusListener onClickCircleStatusListener) {
        this.onClickCircleStatusListener = onClickCircleStatusListener;
    }
}
