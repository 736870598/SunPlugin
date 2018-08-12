package com.sunxy.sunplugina;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sunxy.sunplugin.core.plugin.PluginBaseActivity;

public class MainActivity extends PluginBaseActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.showToast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(that, "plugin_toast", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.toSecondActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(that, SecondActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.startService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(that, MyService.class);
                startService(intent);
            }
        });

        findViewById(R.id.sendBroadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(that, "sssssssss", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction("static.broadcast");
                sendBroadcast(intent);
            }
        });
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        Log.v("sunxy_plugin", "startActivityForResult");
    }
}
