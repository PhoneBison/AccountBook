package com.stu.it.accountbook.fragments;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stu.it.accountbook.R;
import com.stu.it.accountbook.activity.AboutActivity;
import com.stu.it.accountbook.activity.ImportAndExportActivity;
import com.stu.it.accountbook.activity.UnlockGesturePasswordActivity;
import com.stu.it.accountbook.utils.UIUtils;
import com.stu.it.accountbook.view.LoadingPage;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends BaseFragment {
    private LinearLayout mSafeSetting;
    private LinearLayout mImportAndExport;
    private LinearLayout mAbout;
    private LinearLayout mClear;
    //存储密码文件
    private static final String LOCK_PATTERN_FILE = "gesture.key";

    @Override
    protected int load() {
        return LoadingPage.STATUS_SUCCESS;
    }

    @Override
    protected View createSuccessView() {
        View view = UIUtils.inflate(R.layout.more_fragment);
        mSafeSetting = (LinearLayout) view.findViewById(R.id.safe_setting);
        mAbout = (LinearLayout) view.findViewById(R.id.about);
        mImportAndExport = (LinearLayout) view.findViewById(R.id.import_and_export);
        mClear= (LinearLayout) view.findViewById(R.id.clear);
        safeSetting();
        about();
        importAndExport();
        clearSafe();
        return view;
    }

    private void clearSafe() {
        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dataSystemDirectory = UIUtils.getContext().getFilesDir()
                        .getAbsolutePath();
                File sLockPatternFilename = new File(dataSystemDirectory
                        , LOCK_PATTERN_FILE);
                if(sLockPatternFilename.exists()){
                    sLockPatternFilename.delete();
                    Toast.makeText(UIUtils.getContext(), "删除密码成功", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(UIUtils.getContext(), "你还没有设置密码", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void safeSetting() {
        mSafeSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UnlockGesturePasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void about() {
        mAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    private void importAndExport() {
        mImportAndExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImportAndExportActivity.class);
                startActivity(intent);
            }
        });
    }

}
