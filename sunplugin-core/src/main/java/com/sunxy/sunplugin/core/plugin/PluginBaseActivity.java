package com.sunxy.sunplugin.core.plugin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.sunxy.sunplugin.core.comm.InterfaceActivity;
import com.sunxy.sunplugin.core.host.ProxyBroadcast;

/**
 * -- 插件activity需继承这个
 * <p>
 * Created by sunxy on 2018/8/10 0010.
 */
public abstract class PluginBaseActivity extends Activity implements InterfaceActivity {

    /**
     * 代理activity的实力，实际上是在代理activity中调用该类的对应方法。
     */
    protected Activity that;

    @Override
    public void attach(Activity proxyActivity) {
        this.that = proxyActivity;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        this.that = this;
    }

    //--------------插件主动调用---------------------------------
    @Override
    public void setContentView(View view) {
        if (that != null && that != this){
            that.setContentView(view);
        }else{
            super.setContentView(view);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (that != null && that != this){
            that.setContentView(layoutResID);
        }else{
            super.setContentView(layoutResID);
        }
    }

    @Override
    public <T extends View> T findViewById(int id) {
        if (that != null && that != this){
            return that.findViewById(id);
        }
        return super.findViewById(id);
    }

    @Override
    public Intent getIntent() {
        if (that != null && that != this){
            return that.getIntent();
        }
        return super.getIntent();
    }

    @Override
    public ClassLoader getClassLoader() {
        if (that != null && that != this){
            return that.getClassLoader();
        }
        return super.getClassLoader();
    }

    @NonNull
    @Override
    public LayoutInflater getLayoutInflater() {
        if (that != null && that != this){
            return that.getLayoutInflater();
        }
        return super.getLayoutInflater();
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        if (that != null && that != this){
            return that.getApplicationInfo();
        }
        return super.getApplicationInfo();
    }


    @Override
    public Window getWindow() {
        if (that != null && that != this){
            return that.getWindow();
        }
        return super.getWindow();
    }


    @Override
    public WindowManager getWindowManager() {
        if (that != null && that != this){
            return that.getWindowManager();
        }
        return super.getWindowManager();
    }

    //--------------插件被动调用---------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (that == this){
            super.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        if (that == this){
            super.onStart();
        }
    }

    @Override
    public void onResume() {
        if (that == this){
            super.onResume();
        }
    }

    @Override
    public void onPause() {
        if (that == this){
            super.onPause();
        }
    }

    @Override
    public void onStop() {
        if (that == this){
            super.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if (that == this){
            super.onDestroy();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (that == this){
            super.onSaveInstanceState(outState);
        }
    }

    //-------------------------------------------------------------------------------------------

    @Override
    public void startActivity(Intent intent) {
        if (that != null && that != this){
            Intent m = new Intent();
            m.putExtra("className", intent.getComponent().getClassName());
            that.startActivity(m);
        }else{
            super.startActivity(intent);
        }
    }

    @Override
    public ComponentName startService(Intent service) {
        if (that != null && that != this){
            Intent m = new Intent();
            m.putExtra("serviceName", service.getComponent().getClassName());
            return that.startService(m);
        }
        return super.startService(service);
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        if (that != null && that != this){
            that.unregisterReceiver(receiver);
        }else{
            super.unregisterReceiver(receiver);
        }
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        if (that != null && that != this){
            return that.registerReceiver(receiver, filter);
        }
        return super.registerReceiver(receiver, filter);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        if (that != null && that != this){
            that.sendBroadcast(intent);
        }else{
            super.sendBroadcast(intent);
        }

    }
}
