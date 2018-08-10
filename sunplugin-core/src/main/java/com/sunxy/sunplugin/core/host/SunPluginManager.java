package com.sunxy.sunplugin.core.host;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * --
 * <p>
 * Created by sunxy on 2018/8/10 0010.
 */
public class SunPluginManager {

    private static SunPluginManager manager = new SunPluginManager();

    private SunPluginManager(){}

    public static SunPluginManager get(){
        return manager;
    }

    private PackageInfo packageInfo;
    private DexClassLoader dexClassLoader;
    private Resources resources;

    public void loadPluginApk(Context context, String path){
        packageInfo = context.getPackageManager()
                .getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);

        File dexOutFile = context.getDir("plugin_dex", Context.MODE_PRIVATE);
        dexClassLoader = new DexClassLoader(path, dexOutFile.getAbsolutePath()
        , null, context.getClassLoader());

        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, path);
            resources = new Resources(assetManager, context.getResources().getDisplayMetrics(),
                    context.getResources().getConfiguration());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public Resources getResources() {
        return resources;
    }

    public void startActivity(Context context, String className){
        Intent intent1 = new Intent(context, ProxyActivity.class);
        intent1.putExtra("className", className);
        context.startActivity(intent1);
    }
}
