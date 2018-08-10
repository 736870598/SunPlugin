package com.sunxy.sunplugin.core.host;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sunxy.sunplugin.core.comm.InterfaceBroadcast;

import java.lang.reflect.Constructor;

/**
 * --
 * <p>
 * Created by sunxy on 2018/8/10 0010.
 */
public class ProxyBroadcast extends BroadcastReceiver {

    private BroadcastReceiver receiver;
    private String className;

    public ProxyBroadcast(String className){
        this.className = className;
    }

    public ProxyBroadcast(BroadcastReceiver receiver){
        this.receiver = receiver;
    }

    private void initReceiver(){
        try {
            Class<?> aClass = SunPluginManager.get().getDexClassLoader().loadClass(className);
            Constructor<?> constructor = aClass.getConstructor(new Class[]{});
            receiver = (BroadcastReceiver) constructor.newInstance(new Object[]{});
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (receiver == null){
            initReceiver();
        }
        receiver.onReceive(context, intent);
    }
}
