package com.sunxy.sunplugin.core.host;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sunxy.sunplugin.core.comm.InterfaceActivity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * -- 宿主通过跳转到这里 相当于跳转到插件 的功能。
 * <p>
 * Created by sunxy on 2018/8/10 0010.
 */
public class ProxyActivity extends Activity{

    private InterfaceActivity interfaceActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String className = getIntent().getStringExtra("className");

        try {
            Class<?> pluginActivityClass = getClassLoader().loadClass(className);
            Constructor constructor = pluginActivityClass.getConstructor(new Class[]{});
            interfaceActivity = (InterfaceActivity) constructor.newInstance(new Object[]{});
            interfaceActivity.attach(this);
            interfaceActivity.onCreate(savedInstanceState);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        return SunPluginManager.get().getDexClassLoader();
    }

    @Override
    public Resources getResources() {
        return SunPluginManager.get().getResources();
    }

    @Override
    public void onStart() {
        super.onStart();
        interfaceActivity.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        interfaceActivity.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        interfaceActivity.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        interfaceActivity.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        interfaceActivity.onDestroy();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        interfaceActivity.onSaveInstanceState(outState);
    }

    @Override
    public void startActivity(Intent intent) {
        String className = intent.getStringExtra("className");
        Intent intent1 = new Intent(this, ProxyActivity.class);
        intent1.putExtra("className", className);
        super.startActivity(intent1);
    }

    @Override
    public ComponentName startService(Intent service) {
        String serviceName = service.getStringExtra("serviceName");
        Intent intent1 = new Intent(this, ProxyService.class);
        intent1.putExtra("serviceName", serviceName);
        return super.startService(intent1);
    }

    Map<BroadcastReceiver, ProxyBroadcast> broadcastMap = new HashMap();

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        ProxyBroadcast broadcast = new ProxyBroadcast(receiver);
        broadcastMap.put(receiver, broadcast);
        return super.registerReceiver(broadcast, filter);
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        super.unregisterReceiver(broadcastMap.remove(receiver));
    }
}
