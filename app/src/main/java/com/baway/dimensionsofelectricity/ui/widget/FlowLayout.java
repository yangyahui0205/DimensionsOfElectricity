package com.baway.dimensionsofelectricity.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/*
 *@Auther:杨雅慧
 *@Date: 19.6.28
 *@Time: 14:29:29
 *@Description:
 * */public class FlowLayout extends ViewGroup {

    //继承viewgroup重写构造方法
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    //测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量子宽高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getWidth();//获取宽
        int row = 0;//定义常量
        int dieth = 18;//左边距

        for (int i = 0; i < getChildCount(); i++) {
//            坐标
            View view = getChildAt(i);
            int viewwidth = view.getMeasuredWidth();//宽
            int viewheight = view.getMeasuredHeight();//高
            //判断
            if (dieth + viewwidth > width) {
                row++;
                dieth = 18;
            }
            //左上右下
            view.layout(dieth, row * viewheight, viewwidth + dieth, viewheight * (row + 1));
            dieth += viewwidth;
        }
    }
}
