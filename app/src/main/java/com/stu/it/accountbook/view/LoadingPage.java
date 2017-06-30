package com.stu.it.accountbook.view;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.stu.it.accountbook.R;
import com.stu.it.accountbook.utils.ThreadUtils;
import com.stu.it.accountbook.utils.UIUtils;


/**
 * 拆分BaseFragment 中 与FrameLayout相关的代码
 */
public abstract class LoadingPage extends FrameLayout {
    public static final int STATUS_LOADING = 0;//加载中
    public static final int STATUS_UNKNOWN = 1;//未知
    public static final int STATUS_EMPTY = 2;//加载为空
    public static final int STATUS_ERROR = 3;//加载失败
    public static final int STATUS_SUCCESS = 4;//加载成功


    private int status = STATUS_LOADING;// 默认加载中状态

    private View loadingView;//加载中的view对象
    private View errorView;//加载失败的view对象
    private View emptyView;//加载为空的view对象
    private View successView;//加载成功的view对象

    public LoadingPage(Context context) {
        super(context);
        init();
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();//
    }



    /**
     * 添加四种不同的界面
     */
    public void init() {
        if (loadingView == null) {
            loadingView = createLoadingView();// 创建加载中界面
            this.addView(loadingView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    -1));
        }
        if (errorView == null) {
            errorView = createErrorView();// 创建加载中界面
            this.addView(errorView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    -1));
        }
        if (emptyView == null) {
            emptyView = createEmptyView();// 创建加载中界面
            this.addView(emptyView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    -1));
        }

        //  没有添加成功界面  , 在请求服务器成功后, 再去创建成功界面 添加到帧布局中
        showPage();
    }


    /**
     * 根据状态切换不同的界面
     */
    public void showPage() {
        if (loadingView != null) {
            loadingView.setVisibility(status == STATUS_LOADING || status == STATUS_UNKNOWN ? View.VISIBLE : View.INVISIBLE);
        }

        if (errorView != null) {
            errorView.setVisibility(status == STATUS_ERROR ? View.VISIBLE : View.INVISIBLE);
        }
        if (emptyView != null) {
            emptyView.setVisibility(status == STATUS_EMPTY ? View.VISIBLE : View.INVISIBLE);
        }

        //  状态有可能是成功状态
        //如果是成功状态
        if (status == STATUS_SUCCESS) {
            // 1. 创建成功界面
            if (successView == null) {
                successView = createSuccessView();
                // 2. 添加到帧布局中
                this.addView(successView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, -1));
            }else{
                successView.setVisibility(View.VISIBLE);
            }
        }

    }


    /**
     * 创建加载中view对象
     */
    private View createLoadingView() {
        // xml--->View
        return UIUtils.inflate(R.layout.page_loading);
    }


    /**
     * 创建错误界面
     */
    private View createErrorView() {
        View view = UIUtils.inflate(R.layout.page_error);
        view.findViewById(R.id.page_bt).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        return view;
    }

    /**
     * 创建为空界面
     */
    private View createEmptyView() {
        View view = UIUtils.inflate(R.layout.page_empty);
        return view;
    }


    /**
     * 请求服务器数据,根据服务器返回状态切换不同界面
     */
    public void show() {
        // 错误状态 重新修改为 加载中状态
        if (status == STATUS_ERROR){
            status = STATUS_LOADING;
        }


        // 根据状态重新切换界面
        showPage();

        ThreadUtils.runOnBackThread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                // 请求网络 返回对应的状态
                status = load();
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showPage();
                    }
                });
            }
        });
    }

    /**
     * 请求服务器数据返回对应的状态
     * @return
     */
    protected abstract int load();


    /**
     * 创建成功界面
     * @return
     */
    protected abstract View createSuccessView();
}
