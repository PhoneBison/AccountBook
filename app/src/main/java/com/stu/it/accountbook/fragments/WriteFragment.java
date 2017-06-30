package com.stu.it.accountbook.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.stu.it.accountbook.R;
import com.stu.it.accountbook.activity.DateActivity;
import com.stu.it.accountbook.activity.MyApp;
import com.stu.it.accountbook.activity.TimeActivity;
import com.stu.it.accountbook.db.DaoSession;
import com.stu.it.accountbook.db.Info;
import com.stu.it.accountbook.db.Tag;
import com.stu.it.accountbook.db.TagDao;
import com.stu.it.accountbook.utils.UIUtils;
import com.stu.it.accountbook.view.LoadingPage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 */
public class WriteFragment extends BaseFragment {
    private EditText mAmount;
    private Button mDateChoose;
    private ToggleButton mType;
    private Button mDate;
    private Button mTime;
    private GridView mGrid;
    private TextView mNomalTag;
    private EditText mComment;
    private Button mSave;
    private List<Tag> tagInfo;
    private Double money;
    private String comment;
    //上一个点击的图片
    private int lastPosition=-1;

    //获取当前选中图片的ID
    static long currentID;
    //获取当前选中的图片ID
    static Integer tagImageId;
    //获取当前选中的图片名称
    static String tagName;
    //当前选择的类型（默认为支出）
    static int currentType = 0;
    //默认选择支出
    static Boolean isChecked=true;
    //获取数据库操作对象
    DaoSession daoSession = MyApp.getInstance().setupDatabase();



    //记录时间和小时
    static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");
    private SimpleAdapter simpleAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //返回时是否刷新
        show();
    }

    @Override
    protected int load() {
        return LoadingPage.STATUS_SUCCESS;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(currentType == 0){

            tagInfo=daoSession.getTagDao().queryBuilder().where(TagDao.Properties.Type.eq(new Integer(0))).list();
        }else{

            tagInfo=daoSession.getTagDao().queryBuilder().where(TagDao.Properties.Type.eq(new Integer(1))).list();
        }

        if(simpleAdapter!=null){
            simpleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected View createSuccessView() {
        //初始化界面
        View view = UIUtils.inflate(R.layout.write_fragment);
        mAmount = (EditText) view.findViewById(R.id.amount);
        mDateChoose = (Button) view.findViewById(R.id.date);
        mTime = (Button) view.findViewById(R.id.time);
        mComment = (EditText) view.findViewById(R.id.comment);
        mNomalTag= (TextView) view.findViewById(R.id.nomal_tag);
        mGrid= (GridView) view.findViewById(R.id.grid);
        mType= (ToggleButton) view.findViewById(R.id.type);
        mSave = (Button) view.findViewById(R.id.save);


        //选取时间初始化，包括时区的设置
        mDateChoose.setText(sdfYear.format(Calendar.getInstance().getTime()));
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        mTime.setText(c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));

        //选择日期
        chooseDate();
        //选择时间
        chooseTime();
        //获取消费金额
        String money = mAmount.getText().toString().trim();
        //获取消费备注信息
        String comment=mComment.getText().toString().trim();
        //默认显示支出界面
        loadPayTag();
        //判断选择的类型
        mType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    currentType = 0;
                    //加载不同类型的标签
                    loadPayTag();
                }else{
                    currentType = 1;
                    //加载不同类型的标签
                    loadEarningTag();
                }
            }
        });
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //保存消费记录
                accountSave();
            }
        });
        return view;
    }

    //保存消费记录的方法
    private void accountSave() {
        //获取消费金额
        String currentMoney = mAmount.getText().toString().trim();
        if(TextUtils.isEmpty(currentMoney)){
            Toast.makeText(UIUtils.getContext(), "输入金额为空", Toast.LENGTH_SHORT).show();
            return;
        }

        //判断图片是否选择
        if(tagImageId==null){
            Toast.makeText(UIUtils.getContext(), "请选择一个标签", Toast.LENGTH_SHORT).show();
            return;
        }
        money = Double.parseDouble(currentMoney);

        //获取消费备注信息
        comment=mComment.getText().toString().trim();
        //创建存储消费信息的对象
        Info info=new Info();
        info.setDate(mDateChoose.getText().toString()+" "+mTime.getText().toString());
        info.setMoney(money);
        info.setRemark(comment);
        info.setType(currentType);
        info.setTagId((int) getTagId());
        if(money==null){
            Toast.makeText(UIUtils.getContext(),"请输入消费金额",Toast.LENGTH_SHORT).show();
            return;
        }else if("".equals(currentID)){
            Toast.makeText(UIUtils.getContext(),"请选择消费标签",Toast.LENGTH_SHORT).show();
            return;
        }else{
            daoSession.getInfoDao().insert(info);
            Toast.makeText(UIUtils.getContext(),"保存成功",Toast.LENGTH_SHORT).show();
        }
    }

    //加载支出标签
    private void loadPayTag() {
        tagInfo=daoSession.getTagDao().queryBuilder().where(TagDao.Properties.Type.eq(new Integer(0))).list();
        if(tagInfo==null){
            mGrid.setVisibility(View.GONE);
            mNomalTag.setVisibility(View.VISIBLE);
        }else{
            //设置GridView
            setGridView();
        }
    }


    //加载收入标签
    private void loadEarningTag() {
        tagInfo=daoSession.getTagDao().queryBuilder().where(TagDao.Properties.Type.eq(new Integer(1))).list();
        if(tagInfo==null){
            mGrid.setVisibility(View.GONE);
            mNomalTag.setVisibility(View.VISIBLE);
        }else{
            //设置GridView
            setGridView();
        }
    }

    //GridView的初始化
    private void setGridView() {
        final ArrayList<HashMap<String, Object>> imagelist = new ArrayList<HashMap<String, Object>>();
        // 使用HashMap将图片添加到一个数组中，注意一定要是HashMap<String,Object>类型的，因为装到map中的图片要是资源ID，而不是图片本身
        // 如果是用findViewById(R.drawable.image)这样把真正的图片取出来了，放到map中是无法正常显示的
        for (int i = 0; i < tagInfo.size(); i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("image", tagInfo.get(i).getTag());
            map.put("name", tagInfo.get(i).getName());
            imagelist.add(map);
        }

        // 使用simpleAdapter封装数据，将图片显示出来
        simpleAdapter = new SimpleAdapter(UIUtils.getContext(), imagelist,
                R.layout.tag_record_item_template, new String[] { "image", "name" }, new int[] {
                R.id.icon, R.id.name });
        // 设置GridView的适配器为新建的simpleAdapter
        mGrid.setAdapter(simpleAdapter);
        mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ImageView imageView = (ImageView)view.findViewById(R.id.icon);
                TextView tv = (TextView)view.findViewById(R.id.name);
                GridView gridView = (GridView)adapterView.findViewById(R.id.grid_tags);
                tagImageId= (int) imagelist.get(position).get("image");
                tagName= (String) imagelist.get(position).get("name");
                //获取tagId
                getTagId();
                switch (position){
                    case 0:
                        if(position==lastPosition){
                            imageView.setBackgroundResource(R.color.trans);
                        }
                        imageView.setBackgroundResource(R.color.holo_green_dark);
                        Toast.makeText(UIUtils.getContext(),imagelist.get(position).get("name").toString(),Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        if(position==lastPosition){
                            imageView.setBackgroundResource(R.color.trans);
                        }
                        imageView.setBackgroundResource(R.color.holo_green_dark);
                        Toast.makeText(UIUtils.getContext(),"hello2",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(UIUtils.getContext(),"hello+"+position,Toast.LENGTH_SHORT).show();;
                        break;
                    case 3:
                        Toast.makeText(UIUtils.getContext(),"hello+"+position,Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(UIUtils.getContext(),"hello+"+position,Toast.LENGTH_SHORT).show();;
                        break;
                    case 5:
                        Toast.makeText(UIUtils.getContext(),"hello+"+position,Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(UIUtils.getContext(),"hello+"+position,Toast.LENGTH_SHORT).show();;
                        break;
                    case 7:
                        Toast.makeText(UIUtils.getContext(),"hello+"+position,Toast.LENGTH_SHORT).show();
                        break;
                    case 8:
                        Toast.makeText(UIUtils.getContext(),"hello+"+position,Toast.LENGTH_SHORT).show();;
                        break;
                    case 9:
                        Toast.makeText(UIUtils.getContext(),"hello+"+position,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void chooseTime() {
        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TimeActivity.class);
                startActivityForResult(intent, 1002);
            }
        });
    }

    private void chooseDate() {
        mDateChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DateActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 1001) {
            String result_date = data.getStringExtra("result_date");
            if (null != result_date && !"".equals(result_date)) {
                mDateChoose.setText(result_date);
            }
        }
        if (requestCode == 1002 && resultCode == 1003) {
            String result_time = data.getStringExtra("result_time");
            if (null != result_time && !"".equals(result_time)) {
                mTime.setText(result_time);
            }
        }
    }

    public long getTagId() {
        List list=daoSession.getTagDao().queryBuilder().where(daoSession.getTagDao().queryBuilder().and(TagDao.Properties.Type.eq(new Integer(currentType)),
                TagDao.Properties.Tag.eq(tagImageId),TagDao.Properties.Name.eq(tagName))).list();
                Tag currentTag= (Tag) list.get(0);
        currentID=currentTag.getId();
        return currentID;
    }


}
