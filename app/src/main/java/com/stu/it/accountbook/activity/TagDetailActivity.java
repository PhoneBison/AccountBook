package com.stu.it.accountbook.activity;

import android.content.Intent;
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

import static com.stu.it.accountbook.R.id.delete;

/**
 * Created by Touch on 2017/5/15.
 */

public class TagDetailActivity extends BaseActivity{

    private Button mBack;
    private TextView mTypeName;
    private LinearLayout mType;
    private EditText mName;
    private EditText mComment;
    private GridView mTags;
    private Button mDelete;
    private Button mSubmit;
    private int tagType;

    private Intent intent;
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
    private String[] name = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
            , "", "", "", "", "", "", "", ""};
    private TagDao tagDao;
    private Tag getTag;

    @Override
    public int getLayoutResID() {
        return R.layout.tag_manage_detail_activity;
    }

    @Override
    public void initView() {
        mBack= (Button) findViewById(R.id.back);
        mTypeName= (TextView) findViewById(R.id.type_name);
        mType= (LinearLayout) findViewById(R.id.type);
        mName= (EditText) findViewById(R.id.name);
        mComment= (EditText) findViewById(R.id.manger_detail_comment);
        mTags= (GridView) findViewById(R.id.detail_tags);
        mDelete= (Button) findViewById(delete);
        mSubmit= (Button) findViewById(R.id.submit);

        //获取操作TAG和INFO表的对象
        tagDao = daoSession.getTagDao();
        //获取传递的对象
        intent = getIntent();
        getTag = (Tag) intent.getSerializableExtra("post_tag");
        if(getTag.getType()==0){
            mTypeName.setText("支出");
        }else{
            mTypeName.setText("收入");
        }
        mName.setText(getTag.getName());
        mComment.setText(getTag.getTag_remark());
        //设置GridView
        setGridView();
    }

    private void initDialog() {
        //初始化Dialog
        selfDialog=new SelfDialog(TagDetailActivity.this);
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

        //删除
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tagDao.delete(getTag);
                finish();
            }
        });

        //修改
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(intent.getStringExtra("tag_type").equals("支出")){
                    tagType=0;
                }else{
                    tagType=1;
                }
                List updateTagList=daoSession.getTagDao().queryBuilder().where(daoSession.getTagDao().queryBuilder().and(TagDao.Properties.Type.eq(tagType),
                        TagDao.Properties.Name.eq(intent.getStringExtra("tag_name")),TagDao.Properties.Tag_remark.eq(intent.getStringExtra("tag_remark")))).list();
                Tag updateTag= (Tag) updateTagList.get(0);
                //非空判断
                if("".equals(mName.getText().toString().trim())){
                    Toast.makeText(UIUtils.getContext(),"请输入标签名称",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    updateTag.setName(mName.getText().toString().trim());
                }
                //非空判断
                if("".equals(mComment.getText().toString().trim())){
                    Toast.makeText(UIUtils.getContext(),"请输入标签备注",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    updateTag.setTag_remark(mComment.getText().toString().trim());
                }

                updateTag.setTag(tagImageId);
                List isNull=daoSession.getTagDao().queryBuilder().where(daoSession.getTagDao().queryBuilder().and(TagDao.Properties.Type.eq(new Integer(tagType)),
                        TagDao.Properties.Tag.eq(tagImageId),TagDao.Properties.Name.eq(mName.getText().toString().trim()))).list();
                if(isNull==null || isNull.size() == 0){
                    updateTagInfo(updateTag);
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

    private void deleteTagInfo(int deleteTagType,String deleteTagName,String deleteTagRemark) {
        List deleteTag=daoSession.getTagDao().queryBuilder().where(daoSession.getTagDao().queryBuilder().and(TagDao.Properties.Type.eq(deleteTagType),
                TagDao.Properties.Name.eq(deleteTagName),TagDao.Properties.Tag_remark.eq(deleteTagRemark))).list();
        Tag tag1= (Tag) deleteTag.get(0);
        daoSession.getTagDao().delete((Tag) deleteTag.get(0));
        Toast.makeText(UIUtils.getContext(),"删除成功",Toast.LENGTH_SHORT).show();
    }

    private void updateTagInfo(Tag updateTag) {
        daoSession.getTagDao().update(updateTag);
        Toast.makeText(UIUtils.getContext(),"修改成功",Toast.LENGTH_SHORT).show();
    }

}
