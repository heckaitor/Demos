package com.heckaitor.premissiondemo;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * 这个demo验证shouldShowRequestPermissionRationale的结果，何时会在授权之前弹出解释框
 *
 * <p>官网没有解释，修改<b>系统设置中权限对应的选项（允许、提示、禁止）会如何影响返回的结果</b>；
 * 但正常情况下，如果没有修改设置，与官网的论述是一致的</p>
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 1;
    private static final int REQUEST_SETTING    = 2;

    private Dialog explanationDialog;
    private Dialog failureDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        });
    }

    private void checkPermission(final String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            // 1. 第一次申请这个权限，return false，requestPermission弹出系统授权框
            // 注意，第一次的含义还包括：申请之前没有在设置中修改这个权限的选项（默认是提示）如果改为禁止，走2.2
            // 2. 之后，用户可以在系统权限设置中进行更改，有三个选项：允许、提示、禁止，首次安装后默认为提示
            // 之后再申请权限（无论用户之前是否选中过"不再提示"）：
            // 2.1 若修改为提示：return true，requestPermission弹出系统授权框，且"不再提示"未选中，
            // 如果拒绝授权且不选中"不再提示"，下次继续2.1，若拒绝同时选中"不再提示"，系统设置里变成禁止，下次2.2
            // 2.2 禁止：return false，requestPermission不能弹出授权框，直接回调失败的结果
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                showRationalDialog(permission);
            } else {
                requestPermission(permission);
            }
        } else {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void showRationalDialog(final String permission) {
        if (explanationDialog == null) {
            explanationDialog = new AlertDialog.Builder(this)
                    .setMessage("some explanation why I want the permission")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermission(permission);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
        }

        if (!explanationDialog.isShowing()) {
            explanationDialog.show();
        }
    }

    private void requestPermission(final String permission) {
        ActivityCompat.requestPermissions(this, new String[]{ permission }, REQUEST_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Request Permission Success", Toast.LENGTH_SHORT).show();
                } else {
                    // 失败之后又弹出一个提示框，引导用户去设置里更改
                    showFailureDialog();
                }
                break;
            }
            default: break;
        }
    }

    private void showFailureDialog() {
        if (failureDialog == null) {
            failureDialog = new AlertDialog.Builder(this)
                    .setMessage("Request Failed, you can change in Settings!")
                    .setPositiveButton("Go Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            goSettings();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
        }

        if (!failureDialog.isShowing()) {
            failureDialog.show();
        }
    }

    private void goSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:com.heckaitor.premissiondemo"));
        startActivityForResult(intent, REQUEST_SETTING);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_SETTING:{
                break;
            }
            default: break;
        }
    }
}
