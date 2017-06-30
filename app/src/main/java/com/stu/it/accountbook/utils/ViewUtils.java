package com.stu.it.accountbook.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by wangx on 0005.
 */
public class ViewUtils {

    /**
     * 从父 view 中移除 断绝关系
     * @param view
     */
    public static void removeFromParent(View view){
        if (view != null) {
            ViewParent parent = view.getParent();

            if (parent != null && parent instanceof ViewGroup) {// instanceof 类属于
                ViewGroup group = (ViewGroup) parent;
                group.removeView(view);
            }
        }
    }
}
