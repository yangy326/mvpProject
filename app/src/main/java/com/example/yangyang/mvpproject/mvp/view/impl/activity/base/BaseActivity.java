package com.example.yangyang.mvpproject.mvp.view.impl.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.yangyang.mvpproject.mvp.view.contract.base.BaseView;
import com.example.yangyang.mvpproject.widget.dialog.ProgressDialog;
import com.example.yangyang.mvpproject.widget.placeHolder.PlaceHolderView;

public abstract class BaseActivity extends AppCompatActivity  {
    private ProgressDialog progressDialog = null;

    protected PlaceHolderView mPlaceHolderView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initWindows();
        if (initArgs(getIntent().getExtras())) {
            setContentView(getContentLayoutId());
            initWidget();
            initData();
        } else {
            Toast.makeText(this, "Bundle传值错误", Toast.LENGTH_SHORT).show();
        }
    }

    protected void initWindows() {

    }

    protected boolean initArgs(Bundle bundle) {  // Bundle传值拿参数时可以复写这个函数
        return true;
    }

    protected abstract int getContentLayoutId();

    protected void initWidget() {

    }

    protected void initData() {

    }

    protected void showProgress(String message){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }
    protected void hideProgress(){
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }

    protected void setmPlaceHolderView(PlaceHolderView mPlaceHolderView) {
        this.mPlaceHolderView = mPlaceHolderView;
    }
}
