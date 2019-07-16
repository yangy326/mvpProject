package com.example.yangyang.mvpproject.widget.placeHolder;

import android.content.Context;
import android.opengl.Visibility;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yangyang.mvpproject.R;

public class EmptyView extends LinearLayout implements PlaceHolderView {

    private ImageView mEmptyImg;
    private TextView mStatusText;

    private int[] mDrawableIds = new int[]{0, 0};  // 设置不同情况下的图片
    private String[] mTextI = { "数据为空","网络不太好啊"};   //设置不同情况下的文字

    private View[] mBindViews;

    public EmptyView(Context context) {
        super(context);
        init(null, 0);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs,0);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs,0);
    }

    private void init(AttributeSet attrs, int defStyle) {
        inflate(getContext(), R.layout.layout_empty, this);
        mEmptyImg = findViewById(R.id.img_empty);
        mStatusText = findViewById(R.id.txt_empty);


    }

    /**
     * 将外部布局绑定进来
     * @param views
     */

    public void bind(View... views) {
        this.mBindViews = views;
    }

    /**
     * 隐藏外部布局
     * @param visible
     */

    private void changeBindViewVisibility(int visible) {
        final View[] views = mBindViews;
        if (views == null || views.length == 0)
            return;

        for (View view : views) {
            view.setVisibility(visible);
        }
    }

    @Override
    public void triggerEmpty() {
        mEmptyImg.setImageResource(mDrawableIds[0]);
        mStatusText.setText(mTextI[0]);
        setVisibility(VISIBLE);

        changeBindViewVisibility(GONE);

    }

    @Override
    public void triggerNetError() {
        mEmptyImg.setImageResource(mDrawableIds[1]);
        mStatusText.setText(mTextI[1]);
        setVisibility(VISIBLE);

        changeBindViewVisibility(GONE);
    }

    @Override
    public void triggerError(int strRes) {

    }

    @Override
    public void triggerLoading() {

    }

    @Override
    public void triggerOk() {

        setVisibility(GONE);

        changeBindViewVisibility(VISIBLE);

    }

    @Override
    public void triggerOkOrEmpty(boolean isOk) {
        if (isOk) triggerOk();
        else triggerEmpty();

    }
}
