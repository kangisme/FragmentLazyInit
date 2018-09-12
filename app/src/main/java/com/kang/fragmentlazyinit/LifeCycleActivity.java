package com.kang.fragmentlazyinit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.Logger;

/**
 * @author created by kangren on 2018/7/13 15:10
 */
public class LifeCycleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("Activity onCrete");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.d("Activity onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d("Activity onStart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Logger.d("Activity onRestoreInstanceState");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("Activity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d("Activity onPause");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Logger.d("Activity onNewIntent");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.d("Activity onSaveInstance");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d("Activity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("Activity onDestroy");
    }
}
