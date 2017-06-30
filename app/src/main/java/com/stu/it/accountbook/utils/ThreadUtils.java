package com.stu.it.accountbook.utils;

import android.os.Handler;

/**
 * Created by wangx on 0004.
 * 1. 在子线程中执行
 * 2. 可以切换到主线程执行
 */
public class ThreadUtils {

    /**
     * 在子线程中执行的方法
     * @param runnable
     */
    public  static  void runOnBackThread(Runnable runnable){
        new Thread(runnable).start();
    }


    private static Handler mHandler = new Handler();

    /**
     * 在主线程执行
     * @param runnable
     */
    public static void runOnUiThread( Runnable runnable){
        mHandler.post(runnable);
    }

}
