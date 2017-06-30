package com.stu.it.accountbook.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.stu.it.accountbook.R;
import com.stu.it.accountbook.activity.MyApp;
import com.stu.it.accountbook.activity.TagAddActivity;
import com.stu.it.accountbook.activity.TagDetailActivity;
import com.stu.it.accountbook.db.Tag;
import com.stu.it.accountbook.db.TagDao;
import com.stu.it.accountbook.utils.UIUtils;
import com.stu.it.accountbook.view.LoadingPage;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TagFragment extends BaseFragment {


    private ExpandableListView mTags;
    private Button mAdd;
    private List<Tag> tagPayList;
    private List<Tag> tagEarningList;
    TagDao tagDao=MyApp.getInstance().setupDatabase().getTagDao();
    private MyExpandableListViewAdapter adapter;

    @Override
    protected int load() {
        //查询收入和支出分类的消费信息
        tagPayList= (List<Tag>) tagDao.queryBuilder().where(TagDao.Properties.Type.eq(new Integer(0))).list();
        tagEarningList= (List<Tag>) tagDao.queryBuilder().where(TagDao.Properties.Type.eq(new Integer(1))).list();
        return LoadingPage.STATUS_SUCCESS;
    }

    @Override
    public void onResume() {
        super.onResume();
        //查询收入和支出分类的消费信息
        tagPayList= (List<Tag>) tagDao.queryBuilder().where(TagDao.Properties.Type.eq(new Integer(0))).list();
        tagEarningList= (List<Tag>) tagDao.queryBuilder().where(TagDao.Properties.Type.eq(new Integer(1))).list();
        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected View createSuccessView() {
        View view = UIUtils.inflate(R.layout.tag_fragment);
        mAdd= (Button) view.findViewById(R.id.add);
        mTags = (ExpandableListView) view.findViewById(R.id.tags);
        initExpandableListView();
        addTag();
        //ExpandableListView的子条目点击事件
        mTags.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            //int groupPosition, int childPosition, long id
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
                Intent intent=new Intent(UIUtils.getContext(), TagDetailActivity.class);
                //传递点击的对象
                Bundle bundle = new Bundle();
                if(childPosition>=0){
                    if(groupPosition==0){
                        Tag earningChildTag = tagEarningList.get(childPosition);
                        bundle.putSerializable("post_tag", earningChildTag);
                        intent.putExtras(bundle);

                    }else{
                        Tag payChildTag = tagPayList.get(childPosition);
                        bundle.putSerializable("post_tag", payChildTag);
                        intent.putExtras(bundle);
                    }
                    startActivity(intent);
                }
                return true;
            }
        });
        return view;
    }

    private void initExpandableListView(){
        adapter = new MyExpandableListViewAdapter();
        mTags.setAdapter(adapter);
    }

    private void addTag() {
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), TagAddActivity.class);
                startActivityForResult(intent,1004);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1004 && resultCode == 1005)
        {
            Tag tagInfo = data.getParcelableExtra("tagInfo");
            //Long id, Integer type, String tag, String tag_name, String name, String tag_remark
            if(tagInfo!=null){
                MyApp.getInstance().setupDatabase().getTagDao().insert(tagInfo);
            }
        }

    }

    class MyExpandableListViewAdapter extends BaseExpandableListAdapter {


        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getChildrenCount(int i) {
            if(i == 0){
                return tagEarningList.size();
            }else{
                return tagPayList.size();
            }
        }

        @Override
        public Object getGroup(int i) {
            if(i==0){
                return tagEarningList;
            }else{
                return tagPayList;
            }
        }

        @Override
        public Object getChild(int i, int i1) {
            if(i==0){
                return tagEarningList.get(i1);
            }else{
                return tagPayList.get(i1);
            }
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder groupHolder = null;
            if(null == convertView){
                convertView = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.tag_manage_group_template, null);
                groupHolder = new GroupHolder();
                groupHolder.tag = (TextView) convertView.findViewById(R.id.header);
                convertView.setTag(groupHolder);
            }else{
                groupHolder = (GroupHolder)convertView.getTag();
            }
            if(groupPosition == 0) {
                groupHolder.tag.setText("收入标签");
            }else{
                groupHolder.tag.setText("支出标签");
            }
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ItemHolder itemHolder = null;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.tag_manage_item_template, null);
                itemHolder = new ItemHolder();
                itemHolder.tagName = (TextView)convertView.findViewById(R.id.name);
                itemHolder.img = (ImageView)convertView.findViewById(R.id.icon);
                itemHolder.tagReMark = (TextView)convertView.findViewById(R.id.manger_comment);
                convertView.setTag(itemHolder);
            }
            else
            {
                itemHolder = (ItemHolder)convertView.getTag();
            }
            //收入标签
            if(groupPosition == 0){
                itemHolder.tagName.setText(tagEarningList.get(childPosition).getName());
                itemHolder.img.setImageResource(tagEarningList.get(childPosition).getTag());
                itemHolder.tagReMark.setText(tagEarningList.get(childPosition).getTag_remark());
            }else{
                //支出标签
                itemHolder.tagName.setText(tagPayList.get(childPosition).getName());
                itemHolder.img.setImageResource(tagPayList.get(childPosition).getTag());
                itemHolder.tagReMark.setText(tagPayList.get(childPosition).getTag_remark());
            }
            adapter.notifyDataSetChanged();
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
    }

    class GroupHolder
    {
        public TextView tag;
    }

    class ItemHolder
    {
        public ImageView img;

        public TextView tagName;

        public TextView tagReMark;
    }

}
