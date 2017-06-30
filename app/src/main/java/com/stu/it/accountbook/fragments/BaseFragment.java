package com.stu.it.accountbook.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stu.it.accountbook.utils.LogUtil;
import com.stu.it.accountbook.utils.ViewUtils;
import com.stu.it.accountbook.view.LoadingPage;


/**
 * Created by wangx on 0005.
 */
public abstract class BaseFragment extends Fragment {



    private LoadingPage mLoadingPage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 复用framelayout
        // view 只能同时有一个父view,  复用前必须和父view断绝关系
        // FragmentManager
        if (mLoadingPage == null) {
            mLoadingPage = new LoadingPage(getActivity()) {
                @Override
                protected int load() {
                    return BaseFragment.this.load();
                }

                @Override
                protected View createSuccessView() {
                    return BaseFragment.this.createSuccessView();
                }
            };

        }

       // show();// 请求服务器 数据 ,根据服务器返回状态切换不同的界面
        LogUtil.d("%s",this+":: onCreateView");
        return mLoadingPage;
    }

    /**
     * 请求网络
     */
    public void show() {
        mLoadingPage.show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d("%s",this+":: onDestroyView");
        ViewUtils.removeFromParent(mLoadingPage);
    }

    /**
     * 请求 网络 返回对应的状态<br/>
     * {@link LoadingPage#STATUS_ERROR 错误}
     * {@link LoadingPage#STATUS_EMPTY  为空}
     * {@link LoadingPage#STATUS_SUCCESS 成功}
     *
     * @return
     */
    protected abstract int load();

    /**
     * 创建成功界面
     * @return
     */
    protected  abstract View createSuccessView();
}
