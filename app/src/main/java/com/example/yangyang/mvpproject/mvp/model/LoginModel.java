package com.example.yangyang.mvpproject.mvp.model;

import com.example.yangyang.mvpproject.callback.RequestCallBack;
import com.example.yangyang.mvpproject.netService.DataManager;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginModel {
    private DataManager manager;

    public LoginModel(DataManager manager) {
        this.manager = manager;
    }

    private RequestCallBack getUserInfo(RequestCallBack callBack){
         manager.getUserInfo()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
        return callBack;
    }
}
