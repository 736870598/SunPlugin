package com.sunxy.sunplugin.core.comm;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * -- activity的接口，插件和宿主统一履行的标准
 * <p>
 * Created by sunxy on 2018/8/10 0010.
 */
public interface InterfaceActivity {

    public void attach(Activity proxyActivity);

    /**
     * 生命周期
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState);
    public void onStart();
    public void onResume();
    public void onPause();
    public void onStop();
    public void onDestroy();
    public void onSaveInstanceState(Bundle outState);
    public boolean onTouchEvent(MotionEvent event);
    public void onBackPressed();

}
