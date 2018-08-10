package com.sunxy.sunplugin.core.host;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.IBinder;

import com.sunxy.sunplugin.core.comm.InterfaceService;

import java.lang.reflect.Constructor;

/**
 * --
 * <p>
 * Created by sunxy on 2018/8/10 0010.
 */
public class ProxyService extends Service {

    private String serviceName;
    private InterfaceService interfaceService;


    private void init(Intent intent){
        serviceName = intent.getStringExtra("serviceName");
        try {
            Class<?> loadClass = SunPluginManager.get().getDexClassLoader().loadClass(serviceName);
            Constructor<?> constructor = loadClass.getConstructor(new Class[]{});
            interfaceService = (InterfaceService) constructor.newInstance(new Object[]{});
            interfaceService.attach(this);
            interfaceService.onCreate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        init(intent);
        return interfaceService.onBind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (interfaceService == null){
            init(intent);
        }
        return interfaceService.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }


    @Override
    public void onDestroy() {
        interfaceService.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        interfaceService.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        interfaceService.onLowMemory();
        super.onLowMemory();

    }

    @Override
    public void onTrimMemory(int level) {
        interfaceService.onTrimMemory(level);
        super.onTrimMemory(level);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        interfaceService.onUnbind(intent);
        return super.onUnbind(intent);

    }

    @Override
    public void onRebind(Intent intent) {
        interfaceService.onRebind(intent);
        super.onRebind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        interfaceService.onTaskRemoved(rootIntent);
        super.onTaskRemoved(rootIntent);
    }
}
