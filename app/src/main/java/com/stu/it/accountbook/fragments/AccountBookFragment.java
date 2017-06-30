package com.stu.it.accountbook.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.stu.it.accountbook.R;
import com.stu.it.accountbook.activity.MyApp;
import com.stu.it.accountbook.activity.RecordDetailActivity;
import com.stu.it.accountbook.db.Info;
import com.stu.it.accountbook.db.InfoDao;
import com.stu.it.accountbook.db.Tag;
import com.stu.it.accountbook.db.TagDao;
import com.stu.it.accountbook.utils.LogUtil;
import com.stu.it.accountbook.utils.UIUtils;
import com.stu.it.accountbook.view.LoadingPage;

import java.util.List;

import static com.stu.it.accountbook.R.id.amount_unit;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountBookFragment extends BaseFragment {

    private ExpandableListView mAccounts;
    private List<Info> infoPayList;
    private List<Info> infoEarningList;
    InfoDao infoDao = MyApp.getInstance().setupDatabase().getInfoDao();
    TagDao tagDao = MyApp.getInstance().setupDatabase().getTagDao();
    private AccountBookFragment.MyExpandableListViewAdapter adapter;
    private Double sumEarningMoney;
    private Double sumPayMoney;

    @Override
    protected int load() {
        //初始化支出和收入的总和
        sumEarningMoney = 0.0;
        sumPayMoney = 0.0;
        //查询收入和支出分类的消费信息
        infoPayList = (List<Info>) infoDao.queryBuilder().where(InfoDao.Properties.Type.eq(new Integer(0))).list();
        for (int i = 0; i <= infoPayList.size() - 1; i++) {
            sumPayMoney = sumPayMoney + infoPayList.get(i).getMoney();
        }
        LogUtil.d("%s", infoPayList.toString());
        infoEarningList = (List<Info>) infoDao.queryBuilder().where(InfoDao.Properties.Type.eq(new Integer(1))).list();
        for (int i = 0; i <= infoEarningList.size() - 1; i++) {
            sumEarningMoney = sumEarningMoney + infoEarningList.get(i).getMoney();
        }
        LogUtil.d("%s", infoEarningList.toString());
        return LoadingPage.STATUS_SUCCESS;
    }

    @Override
    public void onResume() {
        super.onResume();
        //初始化支出和收入的总和
        sumEarningMoney = 0.0;
        sumPayMoney = 0.0;
        //查询收入和支出分类的消费信息
        infoPayList = (List<Info>) infoDao.queryBuilder().where(InfoDao.Properties.Type.eq(new Integer(0))).list();
        for (int i = 0; i <= infoPayList.size() - 1; i++) {
            sumPayMoney = sumPayMoney + infoPayList.get(i).getMoney();
        }
        LogUtil.d("%s", infoPayList.toString());
        infoEarningList = (List<Info>) infoDao.queryBuilder().where(InfoDao.Properties.Type.eq(new Integer(1))).list();
        for (int i = 0; i <= infoEarningList.size() - 1; i++) {
            sumEarningMoney = sumEarningMoney + infoEarningList.get(i).getMoney();
        }
        LogUtil.d("%s","beidiaole");
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Override
    protected View createSuccessView() {
        //初始化界面
        View view = UIUtils.inflate(R.layout.account_fragment);
        mAccounts = (ExpandableListView) view.findViewById(R.id.accounts);
        initExpandableListView();
        //ExpandableListView的子条目点击事件
        mAccounts.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            //int groupPosition, int childPosition, long id
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
                Intent postIntent = new Intent(UIUtils.getContext(), RecordDetailActivity.class);
                //传递点击的对象
                Bundle bundle = new Bundle();
                if (childPosition >= 0) {
                    if (groupPosition == 0) {
                        Info earningChildInfo = infoEarningList.get(childPosition);
                        bundle.putSerializable("post_info", earningChildInfo);
                        postIntent.putExtras(bundle);
                    } else {
                        Info payChildInfo = infoPayList.get(childPosition);
                        bundle.putSerializable("post_info", payChildInfo);
                        postIntent.putExtras(bundle);
                    }
                    startActivity(postIntent);
                }
                return true;
            }
        });
        return view;
    }

    private void initExpandableListView() {
        adapter = new AccountBookFragment.MyExpandableListViewAdapter();
        mAccounts.setAdapter(adapter);
    }

    class MyExpandableListViewAdapter extends BaseExpandableListAdapter {


        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getChildrenCount(int i) {
            if (i == 0) {
                return infoEarningList.size();
            } else {
                return infoPayList.size();
            }
        }

        @Override
        public Object getGroup(int i) {
            if (i == 0) {
                return infoEarningList;
            } else {
                return infoPayList;
            }
        }

        @Override
        public Object getChild(int i, int i1) {
            if (i == 0) {
                return infoEarningList.get(i1);
            } else {
                return infoPayList.get(i1);
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
            AccountBookFragment.GroupHolder groupHolder = null;
            if (null == convertView) {
                convertView = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.record_day_group_template, null);
                groupHolder = new GroupHolder();
                groupHolder.expandSum = (TextView) convertView.findViewById(R.id.expandSum);
                groupHolder.incomeSum = (TextView) convertView.findViewById(R.id.incomeSum);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (GroupHolder) convertView.getTag();
            }
            if (groupPosition == 0) {
                groupHolder.incomeSum.setText("收入总额:" + sumEarningMoney);
                groupHolder.expandSum.setVisibility(View.GONE);
            } else {
                groupHolder.expandSum.setText("支出总额:" + sumPayMoney);
                groupHolder.incomeSum.setVisibility(View.GONE);
            }
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            AccountBookFragment.ItemHolder itemHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.record_time_item_template, null);
                itemHolder = new ItemHolder();
                itemHolder.tagName = (TextView) convertView.findViewById(R.id.tag_name);
                itemHolder.img = (ImageView) convertView.findViewById(R.id.info_icon);
                itemHolder.desc = (TextView) convertView.findViewById(R.id.desc);
                itemHolder.amount_unit = (TextView) convertView.findViewById(amount_unit);
                itemHolder.amount = (TextView) convertView.findViewById(R.id.amount);
                convertView.setTag(itemHolder);
            } else {
                itemHolder = (ItemHolder) convertView.getTag();
            }
            //收入标签
            if (groupPosition == 0) {
                itemHolder.amount_unit.setVisibility(View.VISIBLE);
                itemHolder.amount.setVisibility(View.GONE);
                Tag tag = tagDao.loadByRowId(infoEarningList.get(childPosition).getTagId());
            //    LogUtil.d("%s", "---------------" + tag.toString());
                if (tag == null) {
                    return convertView;
                } else {
                    itemHolder.tagName.setText(tag.getName());
                    itemHolder.img.setImageResource(tag.getTag());
                    itemHolder.desc.setText("收入" + infoEarningList.get(childPosition).getDate().substring(11));
                    itemHolder.amount_unit.setText("¥" + infoEarningList.get(childPosition).getMoney().toString());
                }
            } else {
                //支出标签
                itemHolder.amount_unit.setVisibility(View.GONE);
                itemHolder.amount.setVisibility(View.VISIBLE);
                Tag tag = tagDao.loadByRowId(infoPayList.get(childPosition).getTagId());
             //   LogUtil.d("%s", ">>>>>>>>>>>>>>>>" + tag.toString());
                if (tag == null) {
                    return convertView;
                } else {
                    itemHolder.tagName.setText(tag.getName());
                    itemHolder.img.setImageResource(tag.getTag());
                    itemHolder.desc.setText("支出" + infoPayList.get(childPosition).getDate().substring(11));
                    itemHolder.amount.setText("¥" + infoPayList.get(childPosition).getMoney().toString());
                }
            }
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
    }

    class GroupHolder {
        //支出总额
        public TextView expandSum;

        //收入总额
        public TextView incomeSum;
    }

    class ItemHolder {
        //标签图片
        public ImageView img;

        //文字描述类别
        public TextView desc;

        //标签名称
        public TextView tagName;

        //支出金额
        public TextView amount_unit;

        //收入金额
        public TextView amount;
    }
}
