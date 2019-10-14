package com.baway.dimensionsofelectricity.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baway.dimensionsofelectricity.R;

public class HomePageSearchBox extends LinearLayout implements View.OnClickListener {
    private ImageView imageView, iv_search;
    private EditText editText;

    public HomePageSearchBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.homepage_search_item, this, true);
        imageView = view.findViewById(R.id.iv_more);
        editText = view.findViewById(R.id.et_search);
        iv_search = view.findViewById(R.id.iv_search);
        imageView.setOnClickListener(this);
        editText.setOnClickListener(this);
        iv_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_more:
                onClickSearchBoxListener.more();
                break;
            case R.id.et_search:
                onClickSearchBoxListener.search();
                break;
            case R.id.iv_search:
                onClickSearchBoxListener.search();
                break;
        }

    }


    //接口回调
    public interface OnClickSearchBoxListener {
        public void search();

        public void more();
    }

    //定义接口
    OnClickSearchBoxListener onClickSearchBoxListener;

    //设置点击事件
    public void setOnClickSearchListener(OnClickSearchBoxListener onClickSearchBoxListener) {
        this.onClickSearchBoxListener = onClickSearchBoxListener;
    }

}
