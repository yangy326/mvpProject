package com.example.yangyang.mvpproject.mvp.presenter.base;

import com.example.yangyang.mvpproject.mvp.view.contract.base.BaseView;

public class BasePresenter <V extends BaseView , T > {

    protected V mview = null;

    public BasePresenter(V mview) {
        this.mview = mview;
    }
}
