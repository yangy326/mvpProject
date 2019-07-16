package com.example.yangyang.mvpproject.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Build;

import java.util.LinkedList;
import java.util.List;

public class MyApp extends Application {

    /**
     * 需要持有context的方法可以传递这个app，避免内存泄漏
     */
    public static MyApp app = null;

    /**
     * Activity管理
     */
    private List<Activity> mActivityList = new LinkedList<>();
    protected ActivityManager mActivityManager;
    protected ActivityLifecycle mActivityLifecycle;



    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

        mActivityManager = ActivityManager.getInstance(this);

        mActivityLifecycle = new ActivityLifecycle(mActivityManager);

        registerActivityLifecycleCallback(mActivityLifecycle);


    }

    public List<Activity> getmActivityList() {
        return mActivityList;
    }

    public void setmActivityList(List<Activity> mActivityList) {
        this.mActivityList = mActivityList;
    }

    /**
     * 添加activity
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (mActivityList != null && mActivityList.size() > 0) {
            if (!mActivityList.contains(activity)) {
                mActivityList.add(activity);
            }
        } else {
            mActivityList.add(activity);
        }
    }

    /**
     * 移除activity
     */
    public void removeActivity(Activity activity) {
        if (mActivityList != null && mActivityList.size() > 0) {
            if (mActivityList.contains(activity)) {
                mActivityList.remove(activity);
            }
        }
    }

    /**
     * 遍历mActivityList，结束每一个activity的声明周期
     */
    public void finishAllActivityt() {
        if (mActivityList != null && mActivityList.size() > 0) {
            for (Activity activity : mActivityList) {
                activity.finish();
            }
        }
    }
    /**
     * 完全退出程序
     */
    public void appExit() {
        finishAllActivityt();
        // 正常退出
        System.exit(0);
    }
    /**
     * 出现异常杀掉进程
     */
    public void appKill() {
        finishAllActivityt();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        this.registerActivityLifecycleCallbacks(callbacks);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void unregisterActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        this.unregisterActivityLifecycleCallbacks(callbacks);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mActivityLifecycle != null) {
            unregisterActivityLifecycleCallback(mActivityLifecycle);
        }
        if (mActivityManager != null) {//释放资源
            this.mActivityManager.release();
            this.mActivityManager = null;
        }
        if(app != null){
            app = null;
        }
    }
}
