package com.kang.fragmentlazyinit;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    private MyAdapter mAdapter;

    private List<TabFragment> mFragments;

    private List<String> mTitles;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = findViewById(R.id.view_pager);
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            mTitles.add("Title" + i);
            TabFragment tabFragment = TabFragment.newInstance("这是第" + i + "个TabFragment", i);
            mFragments.add(tabFragment);
        }
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }

    private class MyAdapter extends FragmentNoStateAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }

}
