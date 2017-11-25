package com.pl.universalcopy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.pl.universalcopy.utils.CountLinkMovementMethod;
import com.pl.universalcopy.utils.IOUtil;
import com.pl.universalcopy.utils.StatusBarCompat;
import com.pl.universalcopy.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wangyan-pd on 2016/11/19.
 */

public class DonateActivity extends BaseActivity {
    private static final String SAVE_PIC_PATH= Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory().getAbsolutePath() : "/mnt/sdcard";//保存到SD卡
    private static final String SAVE_REAL_PATH = SAVE_PIC_PATH+ "/Pictures";//保存的确切位置
    public  static String zhifubao="https://mobilecodec.alipay.com/client_download.htm?qrcode=ap13zwff7wggcfdn80";

    private TextView donateMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setupStatusBarView(this, (ViewGroup) getWindow().getDecorView(), true, R.color.colorPrimary);
        setContentView(R.layout.activity_donate);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.donate);


        findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(SAVE_REAL_PATH,"alipay.jpg");
                if(file.exists()){
                    ToastUtil.show(R.string.picture_saved);
                    sendBrodcast4Update(file);
                    return;
                }else {
                    InputStream is=getResources().openRawResource(R.raw.alipay);
                    try {
                        IOUtil.saveToFile(is,file);
                        ToastUtil.show(R.string.picture_saved);
                        sendBrodcast4Update(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        findViewById(R.id.image1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(SAVE_REAL_PATH,"wechat.jpg");
                if(file.exists()){
                    ToastUtil.show(R.string.picture_saved);
                    sendBrodcast4Update(file);
                    return;
                }else {
                    InputStream is=getResources().openRawResource(R.raw.wechat);
                    try {
                        IOUtil.saveToFile(is,file);
                        ToastUtil.show(R.string.picture_saved);
                        sendBrodcast4Update(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        String donate=getString(R.string.donate_now);


        donateMsg= (TextView) findViewById(R.id.donate_msg);
        donateMsg.setText(Html.fromHtml(getString(R.string.thinks_for_donate)
                +"<br /><br /><a href='"+zhifubao+"'>"+donate+"</a>"));
        donateMsg.setMovementMethod(CountLinkMovementMethod.getInstance());
    }

    private void sendBrodcast4Update(File file) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        sendBroadcast(intent);
    }
}
