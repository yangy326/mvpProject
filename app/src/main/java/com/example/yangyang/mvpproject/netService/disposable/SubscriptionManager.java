package com.example.yangyang.mvpproject.netService.disposable;

import com.example.yangyang.mvpproject.mvp.view.contract.base.BaseView;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @description：订阅关系处理
 */

public class SubscriptionManager {

    private static SubscriptionManager mManager;
    private CompositeDisposable mDisposables;

    /**
     * 分view保存请求
     */
    private Map<BaseView,List<Disposable>> reqMap;

    private SubscriptionManager() {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
            reqMap = new HashMap<>();
        }
    }

    public synchronized void add(BaseView v, Disposable subscription) {
        if (subscription == null) return;
        mDisposables.add(subscription);

        List<Disposable> requestCallBacks = reqMap.get(v);
        if(requestCallBacks == null){
            requestCallBacks = new LinkedList<>();
        }
        requestCallBacks.add(subscription);
        reqMap.put(v,requestCallBacks);
    }

    public synchronized void remove(Disposable t) {
        if (mDisposables != null) {
            mDisposables.remove(t);
        }
    }

    /**
     * @param v 当前发起请求的界面
     */
    public synchronized void removeViewReqs(BaseView v){
        try {
            List<Disposable> disposables = reqMap.get(v);
            if(disposables != null){
                for (Disposable t : disposables){
                    remove(t);
                    if(disposables != null){
                        disposables.remove(t);
                    }
                }
            }
        }catch (Exception e){

        }

    }

    public void clear() {
        if (mDisposables != null) {
            mDisposables.clear();
        }
    }

    public static SubscriptionManager getInstance() {
        if (mManager == null) {
            synchronized (SubscriptionManager.class) {
                if (mManager == null) {
                    mManager = new SubscriptionManager();
                }
            }
        }
        return mManager;
    }

}
