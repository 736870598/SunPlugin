package com.sunxy.sunplugin;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sunxy.sunplugin.core.host.SunPluginManager;

public class MainActivity extends AppCompatActivity {

    String pluginPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/sunxy_file/plugin/pa.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.loadPlugin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SunPluginManager.get().loadPluginApk(MainActivity.this, pluginPath);
            }
        });

        findViewById(R.id.toPluginActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SunPluginManager.get().startActivity(MainActivity.this, SunPluginManager.get().getPackageInfo().activities[0].name);
            }
        });
    }
}
