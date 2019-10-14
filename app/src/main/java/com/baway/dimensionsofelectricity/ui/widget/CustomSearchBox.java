package com.baway.dimensionsofelectricity.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baway.dimensionsofelectricity.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomSearchBox extends LinearLayout{

    @BindView(R.id.iv_custom_more)
    ImageView ivCustomMore;
    @BindView(R.id.et_custom_search)
    EditText etCustomSearch;
    @BindView(R.id.iv_custom_search)
    TextView ivCustomSearch;
    private String content = "";

    public CustomSearchBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View inflate = inflate(getContext(), R.layout.custom_search_item, this);
        ButterKnife.bind(this, inflate);

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        etCustomSearch.setText(content);
    }


    @OnClick({R.id.iv_custom_more, R.id.et_custom_search, R.id.iv_custom_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_custom_search:
                onClickSearchBoxListener.search();
                break;
            case R.id.iv_custom_more:
                onClickSearchBoxListener.moreResult();
                break;
            case R.id.iv_custom_search:
                String content = etCustomSearch.getText().toString().trim();
                onClickSearchBoxListener.searchResult(content);
                break;
        }
    }


    //接口回调
    public interface OnClickSearchBoxListener {
        public void searchResult(String content);

        public void search();

        public void moreResult();
    }

    //定义接口
    OnClickSearchBoxListener onClickSearchBoxListener;

    //设置点击事件
    public void setOnClickSearchBoxListener(OnClickSearchBoxListener onClickSearchBoxListener) {
        this.onClickSearchBoxListener = onClickSearchBoxListener;
    }

}
