package com.sunxy.sunplugina;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sunxy.sunplugin.core.plugin.PluginBaseService;

/**
 * --
 * <p>
 * Created by sunxy on 2018/8/10 0010.
 */
public class MyService extends PluginBaseService {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("sunxy_plugin", "plugin_service_start");
        return super.onStartCommand(intent, flags, startId);
    }
}
