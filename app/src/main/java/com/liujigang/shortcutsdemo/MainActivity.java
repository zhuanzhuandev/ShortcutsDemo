package com.liujigang.shortcutsdemo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * shortcutManager.addDynamicShortcuts();
 * shortcutManager.setDynamicShortcuts();
 * shortcutManager.disableShortcuts();
 * shortcutManager.disableShortcuts();
 * shortcutManager.enableShortcuts();
 * shortcutManager.getDynamicShortcuts()
 * shortcutManager.getManifestShortcuts();
 * shortcutManager.removeAllDynamicShortcuts();
 * shortcutManager.removeDynamicShortcuts();
 * shortcutManager.isRateLimitingActive();
 * shortcutManager.reportShortcutUsed();
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ShortcutManager shortcutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            shortcutManager = getSystemService(ShortcutManager.class);
        }
        findViewById(R.id.getShortcutsInfo).setOnClickListener(this);
        findViewById(R.id.setDynamicShortcuts).setOnClickListener(this);
        findViewById(R.id.updateDynamicShortcuts).setOnClickListener(this);
        findViewById(R.id.addDynamicShortcuts).setOnClickListener(this);
        findViewById(R.id.removeAllDynamicShortcuts).setOnClickListener(this);
        findViewById(R.id.enableShortcuts).setOnClickListener(this);
        findViewById(R.id.disableShortcuts).setOnClickListener(this);
        findViewById(R.id.startSettingsActivity).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            Toast.makeText(getApplicationContext(), "不支持的操作", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()) {
            case R.id.getShortcutsInfo:
                getShortcutsInfo();
                break;
            case R.id.setDynamicShortcuts:
                setDynamicShortcuts();
                break;
            case R.id.updateDynamicShortcuts:
                updateShortcuts();
                break;
            case R.id.addDynamicShortcuts:
                addDynamicShortcuts();
                break;
            case R.id.removeAllDynamicShortcuts:
                removeAllDynamicShortcuts();
                break;
            case R.id.disableShortcuts:
                disableShortcuts();
                break;
            case R.id.enableShortcuts:
                enableShortcuts();
                break;
            case R.id.startSettingsActivity:
                startSettingsActivity();
                break;
        }
    }

    /**
     * 替换并添加所有 shortcut 列表
     */
    @TargetApi(25)
    private void setDynamicShortcuts() {
        ShortcutInfo dynamicShortcut1 = new ShortcutInfo.Builder(this, "dy_blog")
                .setShortLabel("dy blog")
                .setLongLabel("csdn blog page")
                .setIcon(Icon.createWithResource(this, R.drawable.cut_wallet))
                .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("http://blog.csdn.net/")))
                .build();

        ShortcutInfo dynamicShortcut2 = new ShortcutInfo.Builder(this, "dy_settings")
                .setShortLabel("dy settings")
                .setLongLabel("Open Dynamic settings")
                .setDisabledMessage("this shortcut is disabled !!!")
                .setIcon(Icon.createWithResource(this, R.drawable.cut_settings))
                .setIntents(new Intent[]{new Intent(Intent.ACTION_MAIN, Uri.EMPTY, this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)})
                .build();

        ShortcutInfo dynamicShortcut3 = new ShortcutInfo.Builder(this, "dy_target")
                .setShortLabel("dy target")
                .setLongLabel("target github page")
                .setIcon(Icon.createWithResource(this, R.drawable.cut_target))
                .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("http://github.com/")))
                .build();

        shortcutManager.setDynamicShortcuts(Arrays.asList(dynamicShortcut1, dynamicShortcut2, dynamicShortcut3));

    }

    /**
     * 动态添加一个Shortcuts
     */
    @TargetApi(25)
    private void addDynamicShortcuts() {
        ShortcutInfo dynamicShortcut = new ShortcutInfo.Builder(this, "dy_wallet")
                .setShortLabel("dy wallet")
                .setLongLabel("Open baidu page")
                .setIcon(Icon.createWithResource(this, R.drawable.cut_wallet))
                .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.baidu.com/")))
                .build();

        shortcutManager.addDynamicShortcuts(Arrays.asList(dynamicShortcut));
    }

    @TargetApi(25)
    private void disableShortcuts() {
        shortcutManager.disableShortcuts(Collections.singletonList("dy_settings"));
    }

    @TargetApi(25)
    private void enableShortcuts() {
        shortcutManager.enableShortcuts(Collections.singletonList("dy_settings"));
    }

    @TargetApi(25)
    private void removeAllDynamicShortcuts() {
        shortcutManager.removeAllDynamicShortcuts();
    }

    @TargetApi(25)
    private void getShortcutsInfo() {
        int maxShortcutCountPerActivity = shortcutManager.getMaxShortcutCountPerActivity();
        //得到所有固定到桌面Shortcuts的列表
        List<ShortcutInfo> pinnedShortcuts = shortcutManager.getPinnedShortcuts();
        List<ShortcutInfo> manifestShortcuts = shortcutManager.getManifestShortcuts();
        List<ShortcutInfo> dynamicShortcuts = shortcutManager.getDynamicShortcuts();
        int iconMaxHeight = shortcutManager.getIconMaxHeight();
        int iconMaxWidth = shortcutManager.getIconMaxWidth();
        //当应该完全退到后台(无Activity或 Service 在前台时)，对Shortcut操作(添加，删除，修改) 的频率是受限的、
        // isRateLimitingActive()返回结果标示是否受限，true：已受限 其他未受限
        boolean rateLimitingActive = shortcutManager.isRateLimitingActive();
        String info = "maxShortcutCountPerActivity:" + maxShortcutCountPerActivity + "\n\r"
                + "iconMaxHeight:" + iconMaxHeight + "\n\r"
                + "iconMaxWidth:" + iconMaxWidth + "\n\r"
                + "rateLimitingActive:" + rateLimitingActive + "\n\r"
                + "pinnedShortcuts:" + printList(pinnedShortcuts) + "\n\r"
                + "manifestShortcuts:" + printList(manifestShortcuts) + "\n\r"
                + "dynamicShortcuts:" + printList(dynamicShortcuts) + "\n\r";

        Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();
    }

    /**
     * 更新shortcut列表
     */
    @TargetApi(25)
    private void updateShortcuts() {
        ShortcutInfo webShortcut = new ShortcutInfo.Builder(MainActivity.this, "dy_blog")
                .setRank(1)
                .build();

        ShortcutInfo dynamicShortcut = new ShortcutInfo.Builder(MainActivity.this, "dy_settings")
                .setRank(0)
                .build();
        shortcutManager.updateShortcuts(Arrays.asList(webShortcut, dynamicShortcut));
        shortcutManager.reportShortcutUsed("dy_blog");
    }

    @TargetApi(25)
    private String printList(List<ShortcutInfo> list) {
        if (list == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (ShortcutInfo shortcutInfo : list) {
            builder.append(shortcutInfo.getId()).append(" , ");
        }
        return builder.toString();
    }

    private void startSettingsActivity() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

}
