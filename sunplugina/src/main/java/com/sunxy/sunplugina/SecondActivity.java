package com.sunxy.sunplugina;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sunxy.sunplugin.core.plugin.PluginBaseActivity;

/**
 * --
 * <p>
 * Created by sunxy on 2018/8/10 0010.
 */
public class SecondActivity extends PluginBaseActivity {

    private MyBroadcast myBroadcast;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        findViewById(R.id.showToast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(that, "secodActivity Show toast", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.sendBroadCast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("sunxy");
                sendBroadcast(intent);
            }
        });

        injectBroadcast();
    }


    public void injectBroadcast(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("sunxy");
        myBroadcast = new MyBroadcast();
        registerReceiver(myBroadcast, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcast);
    }
}
