package com.stu.it.accountbook.fragments;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangx on 0004.
 * fragment 工厂  生产fragment
 */
public class FragmentFactory {


    private static Map<Integer, BaseFragment> sFragmentMap = new HashMap<Integer, BaseFragment>();
    /**
     * 生产fragment 对象  缓存fragment对象
     * @param position
     * @return
     */
    public  static BaseFragment create(int position){
        // 1.  判断 内存中是否存在 要返回的fragment对象
//            1  有  直接返回
//              2  没有   创建新的fragment对象  ,并且添加到 内存 中 再返回
        BaseFragment fragment = sFragmentMap.get(position);
        if (fragment != null) {
            return fragment;
        }

        switch (position) {
            case 0:
                fragment = new WriteFragment();
                break;
            case 1:
                fragment = new AccountBookFragment();
                break;
            case 2:
                fragment = new TagFragment();
                break;
            case 3:
                fragment = new MoreFragment();
                break;
        }

        if (fragment != null){
            sFragmentMap.put(position, fragment);
        }
        return fragment;
    }
}
