package com.example.yangyang.mvpproject.exception;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.example.yangyang.mvpproject.app.MyApp;


/**
 * Created by huchunjie on 2018/8/14.
 * 全局异常捕获处理
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";

    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例
    private static CrashHandler INSTANCE = new CrashHandler();
    //程序的Context对象
    private Context mContext;

    /** 保证只有一个CrashHandler实例 */
    private CrashHandler() {
    }

    /** 获取CrashHandler实例 ,单例模式 */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        handleException(ex);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Log.e(TAG, "error : ", e);
        }
        MyApp.app.appKill();
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Looper.loop();
            }
        }.start();
        //在此可执行其它操作，如获取设备信息、执行异常登出请求、保存错误日志到本地或发送至服务端
        Log.e(TAG, "error : ", ex);
        return true;
    }

}
