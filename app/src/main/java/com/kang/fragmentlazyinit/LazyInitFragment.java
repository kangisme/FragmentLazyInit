package com.kang.fragmentlazyinit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * tab页面可见时才开始加载数据，支持{@link android.support.v4.app.FragmentPagerAdapter}
 * 和{@link android.support.v4.app.FragmentStatePagerAdapter}
 * @author created by kangren on 2018/7/10 14:39
 */
public abstract class LazyInitFragment extends Fragment {

    private boolean isPrepared = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onUserVisible();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        onUserVisible();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isPrepared = false;
    }

    private void onUserVisible() {
        if (isPrepared && getUserVisibleHint()) {
            loadData();
        }
    }

    protected abstract void loadData();

}
