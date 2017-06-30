package com.stu.it.accountbook.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.stu.it.accountbook.R;
import com.stu.it.accountbook.db.DaoSession;
import com.stu.it.accountbook.db.Tag;
import com.stu.it.accountbook.db.TagDao;
import com.stu.it.accountbook.utils.UIUtils;
import com.stu.it.accountbook.view.SelfDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.stu.it.accountbook.db.TagDao.Properties.Tag;

/**
 * Created by Touch on 2017/5/14.
 */

public class TagAddActivity extends BaseActivity {
    private Button mBack;
    private TextView mTypeName;
    private LinearLayout mType;
    private EditText mName;
    private EditText mComment;
    private GridView mTags;
    private Button mSubmit;
    private int tagType;

    SelfDialog selfDialog;
    //默认选中租房
    static int tagImageId = R.drawable.zuf;
    //获取tag数据库操作对象
    DaoSession daoSession = MyApp.getInstance().setupDatabase();

    //图片资源和内容
    private int[] image = { R.drawable.zuf, R.drawable.yund, R.drawable.yul,
            R.drawable.youx, R.drawable.yingeyp, R.drawable.yil, R.drawable.yao,
            R.drawable.yanj , R.drawable.xuex, R.drawable.qit, R.drawable.other4,
            R.drawable.other3, R.drawable.other2, R.drawable.maj, R.drawable.jij,
            R.drawable.gup, R.drawable.gouw, R.drawable.gongz, R.drawable.fangc,
            R.drawable.diany, R.drawable.dap, R.drawable.cunk, R.drawable.chux,
            R.drawable.chongw, R.drawable.cany, R.drawable.biyt};
//    private String[] name = { "租房", "运动", "娱乐", "游戏", "婴儿用品", "医疗", "药品", "烟酒"
//            , "学习", "其它", "修理", "手机", "唱歌", "麻将", "基金", "股票", "购物", "工资"
//            , "房产", "电影", "打牌", "存款", "出行", "宠物", "餐饮", "避孕套"};
    private String[] name = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
            , "", "", "", "", "", "", "", ""};

    @Override
    public int getLayoutResID() {
        return R.layout.tag_manage_add_activity;
    }

    @Override
    public void initView() {
        mBack= (Button) findViewById(R.id.back);
        mTypeName= (TextView) findViewById(R.id.type_name);
        mType= (LinearLayout) findViewById(R.id.type);
        mName= (EditText) findViewById(R.id.name);
        mComment= (EditText) findViewById(R.id.manger_add_comment);
        mTags= (GridView) findViewById(R.id.grid_tags);
        mSubmit= (Button) findViewById(R.id.submit);
        //设置GridView
        setGridView();
    }

    private void initDialog() {
        //初始化Dialog
        selfDialog=new SelfDialog(TagAddActivity.this);
        selfDialog.setEarningOnclickListener("收入", new SelfDialog.onEarningOnclickListener() {
            @Override
            public void onEarningClick() {
                mTypeName.setText("收入");
                selfDialog.dismiss();
            }
        });
        selfDialog.setPayOnclickListener("支出", new SelfDialog.onPayOnclickListener() {
            @Override
            public void onPayClick() {
                mTypeName.setText("支出");
                selfDialog.dismiss();
            }
        });
        selfDialog.show();

    }

    private void setGridView() {
        final ArrayList<HashMap<String, Object>> imagelist = new ArrayList<HashMap<String, Object>>();
        // 使用HashMap将图片添加到一个数组中，注意一定要是HashMap<String,Object>类型的，因为装到map中的图片要是资源ID，而不是图片本身
        // 如果是用findViewById(R.drawable.image)这样把真正的图片取出来了，放到map中是无法正常显示的
        for (int i = 0; i < 26; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("image", image[i]);
            map.put("name", name[i]);
            imagelist.add(map);
        }
        // 使用simpleAdapter封装数据，将图片显示出来
        // 参数一是当前上下文Context对象
        // 参数二是图片数据列表，要显示数据都在其中
        // 参数三是界面的XML文件，注意，不是整体界面，而是要显示在GridView中的单个Item的界面XML
        // 参数四是动态数组中与map中图片对应的项，也就是map中存储进去的相对应于图片value的key
        // 参数五是单个Item界面XML中的图片ID
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, imagelist,
                R.layout.tag_record_item_template, new String[] { "image", "name" }, new int[] {
                R.id.icon, R.id.name });
        // 设置GridView的适配器为新建的simpleAdapter
        mTags.setAdapter(simpleAdapter);
        mTags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ImageView imageView = (ImageView)view.findViewById(R.id.icon);
                GridView gridView = (GridView)adapterView.findViewById(R.id.grid_tags);
                int currentPosition = position;
                tagImageId= (int) imagelist.get(position).get("image");
                switch (position){
                    case 0:
                        imageView.setBackgroundResource(R.color.holo_green_dark);
                        Toast.makeText(UIUtils.getContext(),"hello+"+position,Toast.LENGTH_SHORT).show();;
                        break;
                    case 1:
                        imageView.setBackgroundResource(R.color.holo_green_dark);
                        Toast.makeText(UIUtils.getContext(),"hello+"+position,Toast.LENGTH_SHORT).show();
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

    @Override
    protected void init() {

    }

    @Override
    protected void initListener() {
        mType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDialog();
            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tag tag=new Tag();
                if(mTypeName.getText().toString().trim().equals("支出")){
                    tag.setType(0);
                    tagType=0;
                }else{
                    tag.setType(1);
                    tagType=1;
                }
                //非空判断
                if("".equals(mName.getText().toString().trim())){
                    Toast.makeText(UIUtils.getContext(),"请输入标签名称",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    tag.setName(mName.getText().toString().trim());
                }
                //非空判断
                if("".equals(mComment.getText().toString().trim())){
                    Toast.makeText(UIUtils.getContext(),"请输入标签备注",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    tag.setTag_remark(mComment.getText().toString().trim());
                }

                tag.setTag(tagImageId);
                List isNull=daoSession.getTagDao().queryBuilder().where(daoSession.getTagDao().queryBuilder().and(TagDao.Properties.Type.eq(new Integer(tagType)),
                        Tag.eq(tagImageId),TagDao.Properties.Name.eq(mName.getText().toString().trim()))).list();
                if(isNull==null || isNull.size() == 0){
                    saveTagInfo(tag);
                }else{
                    Toast.makeText(UIUtils.getContext(), "标签已存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                finish();
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void saveTagInfo(Tag tag) {
        daoSession.getTagDao().insert(tag);
        Toast.makeText(UIUtils.getContext(),"添加成功",Toast.LENGTH_SHORT).show();
    }
}
