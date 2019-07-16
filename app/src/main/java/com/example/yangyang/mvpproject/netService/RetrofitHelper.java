package com.example.yangyang.mvpproject.netService;

import com.example.yangyang.mvpproject.BuildConfig;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    public static final String BASE_URL = "http://www.baidu.com";

    private static RetrofitHelper retrofitHelper = null;




    private RetrofitService retrofitService = null;


    private RetrofitHelper(){
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .client(HttpsFactroy.getClient())

                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitService.class);
    }

    public static RetrofitHelper getInstance(){
        if(retrofitHelper == null){
            synchronized (RetrofitHelper.class){
                if(retrofitHelper == null){
                    retrofitHelper = new RetrofitHelper();
                }
            }
        }
        return retrofitHelper;

    }

    public RetrofitService getRetrofitService(){
        return retrofitService;
    }




}
