package com.example.yangyang.mvpproject.netService;

import com.example.yangyang.mvpproject.base.BaseRsp;

import io.reactivex.Flowable;

public class DataManager {
    private RetrofitService mRetrofitService;

    public DataManager(){
        mRetrofitService = RetrofitHelper.getInstance().getRetrofitService();
    }
    public Flowable<BaseRsp> getUserInfo(){
        return mRetrofitService.getUserInfo();
    }
}
