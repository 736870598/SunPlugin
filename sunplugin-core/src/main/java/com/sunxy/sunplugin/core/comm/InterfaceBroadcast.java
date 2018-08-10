package com.sunxy.sunplugin.core.comm;

import android.content.Context;
import android.content.Intent;

/**
 * --
 * <p>
 * Created by sunxy on 2018/8/10 0010.
 */
public interface InterfaceBroadcast {
    void attach(Context context);

    void onReceive(Context context, Intent intent);

}
