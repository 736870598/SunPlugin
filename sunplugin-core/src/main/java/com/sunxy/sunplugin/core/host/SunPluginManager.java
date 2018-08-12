package com.sunxy.sunplugin.core.host;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.icu.text.StringPrepParseException;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

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

            parseReceivers(context, path);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 解析apk文件的静态广播
     * @param context
     * @param path
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws NoSuchFieldException
     */
    private void parseReceivers(Context context, String path) throws ClassNotFoundException,
            NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchFieldException {
        Class<?> packageParserClass = Class.forName("android.content.pm.PackageParser");
        Object packageParser = packageParserClass.newInstance();
        //该方法需要做兼容处理
        Method parsePackageMethod = packageParserClass.getDeclaredMethod("parsePackage", File.class, int.class);
        Object packageObj = parsePackageMethod.invoke(packageParser, new File(path), PackageManager.GET_ACTIVITIES);
        //这里的 packageObj 就是 PackageParser.Package 里面装载着清单文件中的信息。。。

        //获取清单文件中注册的广播消息
//5868        public final ArrayList<Permission> permissions = new ArrayList<Permission>(0);
//5869        public final ArrayList<PermissionGroup> permissionGroups = new ArrayList<PermissionGroup>(0);
//5870        public final ArrayList<Activity> activities = new ArrayList<Activity>(0);
//5871        public final ArrayList<Activity> receivers = new ArrayList<Activity>(0);
//5872        public final ArrayList<Provider> providers = new ArrayList<Provider>(0);
//5873        public final ArrayList<Service> services = new ArrayList<Service>(0);

        Field receiversField = packageObj.getClass().getDeclaredField("receivers");

        //获取 ArrayList<Activity> receivers  这里的Activity不是常用的，是一个保存解析消息的内部类
        List receivers = (List) receiversField.get(packageObj);

        //获取 Activity 中的 intents 属性/ <? extends IntentFilter>
        Class<?> componentClass = Class.forName("android.content.pm.PackageParser$Component");
        Field intentsField = componentClass.getDeclaredField("intents");

        // 调用generateActivityInfo 方法, 把PackageParser.Activity 转换成 ActivityInfo 拿到类名
        Class<?> packageParser$ActivityClass = Class.forName("android.content.pm.PackageParser$Activity");

        Class<?> packageUserStateClass = Class.forName("android.content.pm.PackageUserState");
        Object defaultUserState = packageUserStateClass.newInstance();

        //public static final ActivityInfo generateActivityInfo(Activity a, int flags, PackageUserState state, int userId)

        Method generateReceiverInfo = packageParserClass.getDeclaredMethod("generateActivityInfo",
                packageParser$ActivityClass, int.class, packageUserStateClass, int.class);

        Class<?> userHandler = Class.forName("android.os.UserHandle");
        Method getCallingUserIdMethod = userHandler.getDeclaredMethod("getCallingUserId");
        int userId = (int) getCallingUserIdMethod.invoke(null);

        for (Object activity : receivers) {
            ActivityInfo info = (ActivityInfo) generateReceiverInfo.invoke(packageParser, activity, 0, defaultUserState, userId);
            BroadcastReceiver broadcastReceiver = (BroadcastReceiver) dexClassLoader.loadClass(info.name).newInstance();
            List<? extends IntentFilter> intents = (List<? extends IntentFilter>) intentsField.get(activity);
            for (IntentFilter intentFilter : intents) {
                context.registerReceiver(broadcastReceiver, intentFilter);
            }
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

    public void startPluginActivity(Context context, String className){
        Intent intent1 = new Intent(context, ProxyActivity.class);
        intent1.putExtra("className", className);
        context.startActivity(intent1);
    }

    public void startPluginService(Context context, String className){
        Intent intent1 = new Intent(context, ProxyService.class);
        intent1.putExtra("serviceName", className);
        context.startService(intent1);
    }
}
