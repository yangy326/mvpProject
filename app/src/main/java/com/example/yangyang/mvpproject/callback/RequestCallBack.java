package com.example.yangyang.mvpproject.callback;

import android.util.Log;

import com.example.yangyang.mvpproject.base.BaseRsp;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Locale;

import javax.net.ssl.SSLException;

import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.adapter.rxjava2.HttpException;

public abstract class RequestCallBack<T> extends DisposableSubscriber<BaseRsp<T>> {

    @Override
    public void onNext(BaseRsp<T> tBaseRsp) {
        if (tBaseRsp.getCode() == 200) {
            onReqSuccess(tBaseRsp.getData());
        }
        else {
            onBizError(tBaseRsp.getCode(),tBaseRsp.getMsg());
        }

    }

    @Override
    public void onError(Throwable e) {
        String msg;
        if ((e instanceof HttpException)) {
            msg = ((retrofit2.HttpException) e).message();
            onNetError(((retrofit2.HttpException) e).code(), msg);
        } else if (e instanceof UnknownHostException) {
            msg = "域名解析失败";
            onNetError(-1001,msg);
        } else if (e instanceof ConnectTimeoutException) {
            msg = "连接超时";
            onNetError(-1002,msg);
        } else if (e instanceof SocketTimeoutException) {
            msg = "Socket连接超时";
            onNetError(-1003,msg);
        } else if (e instanceof NoRouteToHostException) {
            msg = "无法连接远程地址与端口";
            onNetError(-1004,msg);
        } else if (e instanceof SSLException) {
            msg = "SSL失败";
            onNetError(-1005,msg);
        } else if (e instanceof ConnectException) {
            msg = "连接失败";
            onNetError(-1006,msg);
        } else if (e instanceof retrofit2.HttpException) {
            retrofit2.HttpException exception = (retrofit2.HttpException) e;
            msg = exception.message();
            Log.d("HttpException",msg);
            onNetError(exception.code(),msg);
        } else if (e instanceof OnErrorNotImplementedException) {
            msg = e.getMessage();
            onNetError(-1007, msg);
        }
         else {
            msg = "网络请求出错";
            onNetError(-1009, msg);
        }

    }

    @Override
    public void onComplete() {

    }

    protected abstract void onReqSuccess(T t);

    //业务错误
    protected abstract void onBizError(int code, String msg);

    //网络错误
    protected abstract void onNetError(int code, String msg);


}
