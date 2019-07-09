package com.example.yangyang.mvpproject.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    public static final String IS_NOT_ADD_ACTIVITY_LIST = "IS_NOT_ADD_ACTIVITY_LIST";

    private ActivityManager activityManager;

    public ActivityLifecycle(ActivityManager activityManager) {
        this.activityManager = activityManager;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //如果intent包含了此字段,并且为true说明不加入到list
        // 默认为false,如果不需要管理(比如不需要在退出所有activity(killAll)时，退出此activity就在intent加此字段为true)
        boolean isNotAdd = false;
        if (activity.getIntent() != null)
            isNotAdd = activity.getIntent().getBooleanExtra(IS_NOT_ADD_ACTIVITY_LIST, false);

        if (!isNotAdd)
            activityManager.addActivity(activity);

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        activityManager.setCurrentActivity(activity);

    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (activityManager.getCurrentActivity() == activity) {
            activityManager.setCurrentActivity(null);
        }

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activityManager.removeActivity(activity);
    }
}
