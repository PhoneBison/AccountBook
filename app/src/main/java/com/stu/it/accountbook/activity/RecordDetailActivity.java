package com.stu.it.accountbook.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.stu.it.accountbook.R;
import com.stu.it.accountbook.db.Info;
import com.stu.it.accountbook.db.InfoDao;
import com.stu.it.accountbook.db.Tag;
import com.stu.it.accountbook.db.TagDao;
import com.stu.it.accountbook.utils.UIUtils;

/**
 * Created by Touch on 2017/5/16.
 */

public class RecordDetailActivity extends BaseActivity {
    private Button mBack;
    private ImageView mTagIcon;
    private TextView mTagName;
    private TextView mTagComment;
    private TextView mAmountDesc;
    private TextView mDatetime;
    private TextView mComment;
    private Button mDelete;
    private Button mEdit;
    private Info getInfo;
    private InfoDao infoDao;
    private TagDao tagDao;
    private Intent intent;


    @Override
    public int getLayoutResID() {
        return R.layout.record_detail_activity;
    }

    @Override
    public void initView() {
        mBack= (Button) findViewById(R.id.back);
        mTagIcon= (ImageView) findViewById(R.id.tag_icon);
        mTagName= (TextView) findViewById(R.id.tag_name);
        mTagComment= (TextView) findViewById(R.id.tag_comment);
        mAmountDesc= (TextView) findViewById(R.id.amount_desc);
        mDatetime= (TextView) findViewById(R.id.datetime);
        mComment= (TextView) findViewById(R.id.comment);
        mDelete= (Button) findViewById(R.id.delete);
        mEdit= (Button) findViewById(R.id.edit);

        //获取操作TAG和INFO表的对象
        infoDao = MyApp.getInstance().setupDatabase().getInfoDao();
        tagDao = MyApp.getInstance().setupDatabase().getTagDao();

        //获取传递的对象
        intent = getIntent();
        getInfo = (Info) intent.getSerializableExtra("post_info");
        //根据传递对象的tagId获取TAG对象
        Tag tag= tagDao.loadByRowId(getInfo.getTagId());
        if(tag != null) {
            //设置显示的内容
            mTagIcon.setImageResource(tag.getTag());
            mTagName.setText(tag.getName());
            mTagComment.setText(tag.getTag_remark());
        }
        //设置显示消费金额的方法
        setAccountDesc();
        mDatetime.setText(getInfo.getDate());
        mComment.setText(getInfo.getRemark());

    }

    //显示消费金额
    private void setAccountDesc() {
        if(getInfo.getType()==1){
            Drawable drawable = getResources().getDrawable(R.drawable.income_icon);
            //设置边界
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mAmountDesc.setCompoundDrawables(drawable,null,null,null);
            mAmountDesc.setText("收入"+getInfo.getMoney());
        }else{
            Drawable drawable = getResources().getDrawable(R.drawable.expand_icon);
            //设置边界
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mAmountDesc.setCompoundDrawables(drawable,null,null,null);
            mAmountDesc.setText("支出"+getInfo.getMoney());
        }
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initListener() {
        //删除传递过来的对象
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoDao.delete(getInfo);
                finish();
            }
        });

        //编辑传递过来的对象
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateIntent=new Intent(UIUtils.getContext(),UpdateRecordActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("update_info", getInfo);
                updateIntent.putExtras(bundle);
                startActivity(updateIntent);
                finish();
            }
        });

        //返回按钮
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
