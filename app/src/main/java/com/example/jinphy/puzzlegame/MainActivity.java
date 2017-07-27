package com.example.jinphy.puzzlegame;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static final int CODE_PERMISSION_WRITE_EXTERNAL_STORAGE = 0;

    private Toolbar toolbal;
    private FloatingActionButton fab;
    private RxPermissions rxPermissions;

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // android原生申请权限的方法
        requestWriteStoragePermission();

        // 使用RxPermission申请权限的方法
        rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE);



        Log.d(TAG, "timeStamp: "+ FileUtils.getFileNameTimeStamp());

    }

    // 申请读写文件的权限并读写文件
    private void requestWriteStoragePermission() {
        // 检查应用是否拥有需要的权限
        if (ContextCompat.checkSelfPermission(
                MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            // 此时该应用没有相应的权限

            // 当用户第一次拒绝授予权限，又第二次想要用该功能是，则该方法会返回true
            // 表明用户不了解用该权限是来干嘛的，此时应该向用户解释为什么需要该权限
            // 然后在去申请权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("申请该权限的作用是读写文件，您必须允许后才能执行相关操作")
                        .setCancelable(true)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("权限解释")
                        .setPositiveButton("知道了", (var1, var2) -> {
                            ActivityCompat.requestPermissions(
                                    this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MainActivity.CODE_PERMISSION_WRITE_EXTERNAL_STORAGE);
                        })
                        .create().show();
            } else {
                // 如果上面的方法返回false，则不需要向用户解释申请权限的原因，直接申请
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MainActivity.CODE_PERMISSION_WRITE_EXTERNAL_STORAGE);
            }
        } else {
            // 用户已经拥有该权限，可以直接操作
            writeFile();
        }

    }

    private void writeFile() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic_1);
        Log.d(TAG, "onCreate: bitmap size:"+bitmap.getByteCount());
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        ImageUtils.bitmapToFile(bitmap,path+ File.separator+"a/a.jpg",50);

        bitmap = ImageUtils.getSmallBitmap(getResources(),R.drawable.pic_1,50,50);
        Log.d(TAG, "onCreate: bitmap size:"+bitmap.getByteCount());

        ImageUtils.bitmapToFile(bitmap,path+ File.separator+"a/b.jpg",100);

        bitmap = ImageUtils.getSmallBitmap(path + File.separator + "a/b.jpg", 50, 50);
        Log.d(TAG, "onCreate: bitmap size:"+bitmap.getByteCount());

        FileUtils.createFileNoRepeatName(path+"/a", "temp", "txt");

        FileUtils.createFileNoRepeatName(path+"/a", "temp", "txt");
        FileUtils.createFileNoRepeatName(path+"/a", "temp", "txt");
    }

    public void init() {
        toolbal = ((Toolbar) findViewById(R.id.toolbar));
        this.setSupportActionBar(toolbal);

        fab = ((FloatingActionButton) findViewById(R.id.fab));
        fab.setOnClickListener(this::onClick);
    }

    public void onClick(View view) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MainActivity.CODE_PERMISSION_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    writeFile();
                } else {
                    Toast.makeText(this, "您拒绝了该项权限的申请", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
