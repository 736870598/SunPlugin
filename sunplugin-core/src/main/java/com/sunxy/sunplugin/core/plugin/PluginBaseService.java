package com.sunxy.sunplugin.core.plugin;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.sunxy.sunplugin.core.comm.InterfaceService;

/**
 * --
 * <p>
 * Created by sunxy on 2018/8/10 0010.
 */
public abstract class PluginBaseService extends Service implements InterfaceService {

    private Service that;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        that = this;
    }

    @Override
    public void attach(Service proxyService) {
        that = proxyService;
    }

    @Override
    public void onCreate() {
        if (that == this){
            super.onCreate();
        }

    }

    @Override
    public void onStart(Intent intent, int startId) {
        if (that == this){
            super.onStart(intent, startId);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (that == this){
            return super.onStartCommand(intent, flags, startId);
        }
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        if (that == this){
            super.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (that == this){
            super.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onLowMemory() {
        if (that == this){
            super.onLowMemory();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        if (that == this){
            super.onTrimMemory(level);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (that == this){
            return super.onUnbind(intent);
        }
        return false;
    }

    @Override
    public void onRebind(Intent intent) {
        if (that == this){
            super.onRebind(intent);
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        if (that == this){
            super.onTaskRemoved(rootIntent);
        }
    }
}
