package com.baway.dimensionsofelectricity.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baway.dimensionsofelectricity.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.14
 *@Time: 19:33:01
 *@Description:
 * */public class CustomSubTractor extends LinearLayout {
    @BindView(R.id.tv_remove)
    TextView tvRemove;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    private int count = 1;

    public CustomSubTractor(Context context) {
        super(context);
    }

    public CustomSubTractor(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(getContext(), R.layout.custom_subtractor_item, this);
        ButterKnife.bind(view);
    }

    public CustomSubTractor(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        tvCount.setText(count + "");
    }

    @OnClick({R.id.tv_remove, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_remove:
                if (count > 0) {
                    --count;
                    tvCount.setText(count + "");
                    onClickSubTractorListener.subTractor(count);
                } else {
                    Toast.makeText(getContext(), "该宝贝不能减少了呦~", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_add:
                ++count;
                tvCount.setText(count + "");
                onClickSubTractorListener.subTractor(count);
                break;
        }
    }

    OnClickSubTractorListener onClickSubTractorListener;

    public interface OnClickSubTractorListener {
        public void subTractor(int count);
    }

    public void setOnClickSubTractorListener(OnClickSubTractorListener onClickSubTractorListener) {
        this.onClickSubTractorListener = onClickSubTractorListener;
    }
}
