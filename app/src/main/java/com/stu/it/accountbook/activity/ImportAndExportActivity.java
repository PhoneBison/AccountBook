package com.stu.it.accountbook.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stu.it.accountbook.R;
import com.stu.it.accountbook.utils.SaveDB;
import com.stu.it.accountbook.utils.UIUtils;

/**
 * Created by Touch on 2017/5/11.
 */
public class ImportAndExportActivity extends BaseActivity {
    private Button mBack;
    private TextView mExport;

    @Override
    public int getLayoutResID() {
        return R.layout.export_activity;
    }

    @Override
    public void initView() {
        mBack= (Button) findViewById(R.id.back);
        mExport= (TextView) findViewById(R.id.export);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initListener() {
        //导出数据监听
        mExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveDB db = new SaveDB();
                Toast.makeText(UIUtils.getContext(), db.Save(),Toast.LENGTH_SHORT).show();
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
