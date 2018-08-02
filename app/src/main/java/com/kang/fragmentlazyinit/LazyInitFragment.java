package com.kang.fragmentlazyinit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * tab页面可见时才开始加载数据，支持{@link android.support.v4.app.FragmentPagerAdapter}
 * 和{@link android.support.v4.app.FragmentStatePagerAdapter}
 * @author created by kangren on 2018/7/10 14:39
 */
public abstract class LazyInitFragment extends Fragment {

    private boolean isFirstResume = true;

    private boolean isFirstVisible = true;

    private boolean isPrepared = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        }
    }

    private synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
            onPrepared();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFirstResume = true;
        isFirstVisible = true;
        isPrepared = false;
    }

    /**
     * 第一次可见时调用
     */
    protected abstract void onFirstUserVisible();

    /**
     * 再次可见时调用
     */
    protected abstract void onUserVisible();

    /**
     * 加载前初始化
     */
    protected abstract void onPrepared();
}
