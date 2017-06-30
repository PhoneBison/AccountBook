package com.stu.it.accountbook.activity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.stu.it.accountbook.R;

/**
 * Created by Touch on 2017/5/11.
 */
public class AboutActivity extends BaseActivity {

    private Button mBack;
    private TextView mVersion;
    private TextView mFavor;
    private TextView mEmail;
    private TableLayout mTableProducts;
    private LinearLayout mProducts;

    @Override
    public int getLayoutResID() {
        return R.layout.about_activity;
    }

    @Override
    public void initView() {
        mBack= (Button) findViewById(R.id.back);
        mVersion= (TextView) findViewById(R.id.version);
        mFavor= (TextView) findViewById(R.id.favor);
        mEmail= (TextView) findViewById(R.id.email);
        mTableProducts= (TableLayout) findViewById(R.id.table_products);
        mProducts= (LinearLayout) findViewById(R.id.products);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
