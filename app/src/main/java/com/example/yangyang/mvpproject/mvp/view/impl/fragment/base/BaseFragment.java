package com.example.yangyang.mvpproject.mvp.view.impl.fragment.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yangyang.mvpproject.mvp.view.contract.base.BaseView;
import com.example.yangyang.mvpproject.netService.disposable.SubscriptionManager;
import com.example.yangyang.mvpproject.widget.dialog.ProgressDialog;

import java.util.List;

public abstract class BaseFragment extends Fragment {

    private boolean isLastVisible = false;
    private boolean hidden = false;
    private boolean isFirst = true;
    private boolean isResuming = false;
    private boolean isViewDestroyed = false;

    private ProgressDialog progressDialog;

    protected View parentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLastVisible = false;
        hidden = false;
        isFirst = true;
        isViewDestroyed = false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(getLayoutResId(), container, false);
        return parentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        finishCreateView(savedInstanceState);
    }

    /**
     *  把Fragment里的请求绑到生命周期上
     */
    public abstract BaseView bindToFragmentCycle();

    /**
     * 添加layout
     * @return
     */
    public abstract int getLayoutResId();

    /**
     * view完成了创建
     * @param state
     */
    public abstract void finishCreateView(Bundle state);
    /**
     * Fragment 可见时回调
     *
     * @param isFirst       是否是第一次显示
     * @param isViewDestroyed Fragment中的View是否被回收过。
     * 存在这种情况：Fragment 的 View 被回收，但是Fragment实例仍在。
     */
    protected void onFragmentResume(boolean isFirst, boolean isViewDestroyed) {
    }

    /**
     * Fragment 不可见时回调
     */
    protected void onFragmentPause() {
    }

    /**
     * fragment里面显示loading
     */
    protected void showProgressDialog(String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            //progressDialog.setTextView(msg);
            progressDialog.show();
        }
    }

    /**
     * fragment隐藏loading
     */
    protected void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isResuming = true;
        tryToChangeVisibility(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        isResuming = false;
        tryToChangeVisibility(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        setUserVisibleHintClient(isVisibleToUser);
    }

    private void setUserVisibleHintClient(boolean isVisibleToUser) {
        tryToChangeVisibility(isVisibleToUser);
        if (isAdded()) {
            // 当Fragment不可见时，其子Fragment也是不可见的。因此要通知子Fragment当前可见状态改变了。
            List<Fragment> fragments = getChildFragmentManager().getFragments();
            if (fragments != null) {
                for (Fragment fragment : fragments) {
                    if (fragment instanceof BaseFragment) {
                        ((BaseFragment) fragment).setUserVisibleHintClient(isVisibleToUser);
                    }
                }
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        onHiddenChangedClient(hidden);
    }

    public void onHiddenChangedClient(boolean hidden) {
        this.hidden = hidden;
        tryToChangeVisibility(!hidden);
        if (isAdded()) {
            List<Fragment> fragments = getChildFragmentManager().getFragments();
            if (fragments != null) {
                for (Fragment fragment : fragments) {
                    if (fragment instanceof BaseFragment) {
                        ((BaseFragment) fragment).onHiddenChangedClient(hidden);
                    }
                }
            }
        }
    }

    private void tryToChangeVisibility(boolean tryToShow) {
        // 上次可见
        if (isLastVisible) {
            if (tryToShow) {
                return;
            }
            if (!isFragmentVisible()) {
                onFragmentPause();
                isLastVisible = false;
            }
            // 上次不可见
        } else {
            boolean tryToHide = !tryToShow;
            if (tryToHide) {
                return;
            }
            if (isFragmentVisible()) {
                onFragmentResume(isFirst, isViewDestroyed);
                isLastVisible = true;
                isFirst = false;
            }
        }
    }

    //Fragment是否可见
    public boolean isFragmentVisible() {
        if (isResuming()
                && getUserVisibleHint()
                && !hidden) {
            return true;
        }
        return false;
    }

    //Fragment 是否在前台
    private boolean isResuming() {
        return isResuming;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewDestroyed = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(bindToFragmentCycle() != null){
            SubscriptionManager.getInstance().removeViewReqs(bindToFragmentCycle());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
