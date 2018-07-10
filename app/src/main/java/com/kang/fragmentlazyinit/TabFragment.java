package com.kang.fragmentlazyinit;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

/**
 * @author created by kangren on 2018/7/6 15:42
 */
public class TabFragment extends LazyInitFragment {

    private static final String DATA = "data";

    private static final String INDEX = "index";

    private View rootView;

    private Context mContext;

    private TextView mTextView;

    private ProgressBar mProgressBar;

    private int mIndex;

    public static TabFragment newInstance(String data, int i) {
        Bundle bundle = new Bundle();
        bundle.putString(DATA, data);
        bundle.putInt(INDEX, i);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(bundle);
//        Logger.d("newInstance");
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mIndex = bundle.getInt(INDEX);
        }
        Logger.d(mIndex + "setUserVisibleHint" + isVisibleToUser);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
        Logger.d(mIndex + "onCreate");
    }


    @Override
    public void onResume() {
        super.onResume();
        Logger.d(mIndex + "onResume");
    }

    @Override
    protected void onFirstUserVisible() {
        initView();
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onPrepared() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Logger.d(mIndex + "onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d(mIndex + "onActivityCreated");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.d(mIndex + "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(mIndex + "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.d(mIndex + "onDetach");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tab, container, false);
//        initView(rootView);
        Logger.d(mIndex + "onCreateView");
        return rootView;
    }


    private void initView() {
        mTextView = rootView.findViewById(R.id.text);
        mProgressBar = rootView.findViewById(R.id.process_bar);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Bundle data = getArguments();
                if (data != null) {
                    String text = data.getString(DATA);
                    mTextView.setText(text);
                    mProgressBar.setVisibility(View.GONE);
                    mTextView.setVisibility(View.VISIBLE);
                }
            }
        }, 2000);

    }
}
