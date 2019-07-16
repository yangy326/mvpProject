package com.example.yangyang.mvpproject.netService;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpsFactroy {
    private static final int CONNECT_TIMEOUT = 10;
    private static final int READ_TIMEOUT = 10;

    //得到请求client
    public static OkHttpClient getClient(){
        //日志显示级别
        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(message -> Log.i("HTTPS","OkHttp====Message:"+message));
        loggingInterceptor.setLevel(level);
        OkHttpClient client = null;
        try {
            client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(new HeaderIntercepter())

                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT,TimeUnit.SECONDS)
                    .build();
        } catch (Exception e) {
            Log.e("error", Log.getStackTraceString(e));
        }

        return client;
    }


    /**
     * 用于动态添加header
     */
    private static class HeaderIntercepter implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain
                    .request()
                    .newBuilder()
                    /*.addHeader()*/
                    .build();
                    return chain.proceed(request);
        }
    }
}
