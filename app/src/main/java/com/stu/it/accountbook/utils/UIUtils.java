package com.stu.it.accountbook.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import com.stu.it.accountbook.activity.MyApp;

/**
 * Created by wangx on 0004.
 */
public class UIUtils {
    /**
     * 根据 资id  回去 字符串数组
     * @param id
     * @return
     */
    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    public static Context getContext() {
        return MyApp.getInstance();
    }

    /**
     * 根据布局id  获取view对象
     * @param id
     * @return
     */
    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }
}
