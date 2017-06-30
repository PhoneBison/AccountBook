package com.stu.it.accountbook.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.stu.it.accountbook.R;

public class SplashActivity extends BaseActivity {

    private TextView tv_version;
    private String versionName;
    private int versionCode;
    private PackageManager pm;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                //跳转到主页面
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                //关闭页面
                finish();
            }

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        handler.sendEmptyMessageDelayed(0,2000);
    }

    @Override
    public int getLayoutResID() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        tv_version=(TextView) findViewById(R.id.tv_version);
    }

    @Override
    protected void init() {
        //获取当前版本信息
        getVersionInfo();
        //展示当前版本名和版本号
        tv_version.setText(versionName+"简洁版");
    }

    @Override
    protected void initListener() {

    }

    private void getVersionInfo() {
        pm = getPackageManager();
        try {
            //packageinfo是对整个清单文件的封装
            PackageInfo packageInfo = pm.getPackageInfo("com.stu.it.accountbook", 0);
            //版本号和版本名
            versionCode=packageInfo.versionCode;
            versionName=packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}
