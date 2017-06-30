package com.stu.it.accountbook.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.stu.it.accountbook.R;

/**
 * Created by Touch on 2017/5/14.
 */

public class SelfDialog extends Dialog {
    private Button mPay;//确定按钮
    private Button mEarning;//取消按钮
    //确定文本和取消文本的显示内容
    private String payStr, earningStr;

    private onPayOnclickListener PayOnclickListener;//支出按钮被点击了的监听器
    private onEarningOnclickListener EarningOnclickListener;//收入按钮被点击了的监听器

    public SelfDialog(Context context) {
        super(context);
    }

    public SelfDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SelfDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_choose_dialog);
        mPay= (Button) findViewById(R.id.pay);
        mEarning= (Button) findViewById(R.id.earning);

        //设置确定按钮被点击后，向外界提供监听  
        mEarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EarningOnclickListener != null) {
                    EarningOnclickListener.onEarningClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听  
        mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PayOnclickListener != null) {
                    PayOnclickListener.onPayClick();
                }
            }
        });
        
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

    }

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param str
     * @param onPayOnclickListener
     */
    public void setPayOnclickListener(String str, onPayOnclickListener onPayOnclickListener) {
        if (str != null) {
            payStr = str;
        }
        this.PayOnclickListener = onPayOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param str
     * @param onEarningOnclickListener
     */
    public void setEarningOnclickListener(String str, onEarningOnclickListener onEarningOnclickListener) {
        if (str != null) {
            earningStr = str;
        }
        this.EarningOnclickListener = onEarningOnclickListener;
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onEarningOnclickListener {
        public void onEarningClick();
    }

    public interface onPayOnclickListener {
        public void onPayClick();
    }
}