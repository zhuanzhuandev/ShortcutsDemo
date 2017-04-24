package com.liujigang.shortcutsdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by liujigang on 2017/4/24.
 */

public class StaticShortcutActivity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String action = getIntent().getAction();
        TextView textView = new TextView(getApplicationContext());
        textView.setText("静态Shortcut \n\r" + action);
        setContentView(textView);
    }
}
