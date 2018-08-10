package com.sunxy.sunplugina;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * --
 * <p>
 * Created by sunxy on 2018/8/10 0010.
 */
public class MyBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "plugin_MyBroadcast", Toast.LENGTH_SHORT).show();
    }
}
