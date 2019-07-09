package com.example.yangyang.mvpproject.netService;

import com.example.yangyang.mvpproject.base.BaseRsp;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface RetrofitService {
    @GET(Server.getUserInfo)
    Flowable<BaseRsp> getUserInfo();


}
