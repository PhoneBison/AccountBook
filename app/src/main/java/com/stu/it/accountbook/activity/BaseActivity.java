package com.stu.it.accountbook.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Touch on 2017/3/18.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        //查找控件
        initView();
        //初始化
        init();
        //设置监听
        initListener();
    }

    /*
    * 获取布局文件资源ID
    * 子类确定布局文件ID
    * */
    public abstract int getLayoutResID();

    /*
    * 初始化View
    * 1.查找控件（所有的子Activity查找控件都放在这个方法里）
    * */
    public abstract void initView();

    /*
    * 初始化数据
    * */
    protected abstract void init();

    /*
    * 设置监听
    * */
    protected   abstract void initListener();
}
