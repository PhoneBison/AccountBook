package com.stu.it.accountbook.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;

import com.stu.it.accountbook.R;
import com.stu.it.accountbook.fragments.BaseFragment;
import com.stu.it.accountbook.fragments.FragmentFactory;
import com.stu.it.accountbook.utils.UIUtils;

import static android.R.attr.fragment;


/**
 * Created by Touch on 2017/3/18.
 */
public class MainActivity extends BaseActivity{

    private ViewPager mViewPager;
    private String[] tabNames;

    @Override
    public int getLayoutResID() {
        Intent intent=getIntent();
        Boolean is_Unlock=intent .getBooleanExtra("UNLOCK_OK",false);
        if(is_Unlock){

        }else{
            if(MyApp.getInstance().getLockPatternUtils().savedPatternExists()){
                startActivity(new Intent(this, UnlockGesturePasswordActivity.class));
                //finish();
            }
        }
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mViewPager = (ViewPager)findViewById(R.id.pager);

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                BaseFragment fragment = FragmentFactory.create(position);
                fragment.show();
            }
        });
    }

    @Override
    protected void init() {

        tabNames= UIUtils.getStringArray(R.array.tab_names);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        //tab修改下划线颜色
        PagerTabStrip mPagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
        mPagerTabStrip.setTabIndicatorColorResource(R.color.holo_green_dark);
    }

    @Override
    protected void initListener() {

    }

    class MyPagerAdapter extends FragmentPagerAdapter{
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //获取ViewPager页面的数量
        @Override
        public int getCount() {
            return tabNames.length;
        }

        //返回Fragment的对象
        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.create(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabNames[position];
        }
    }

}
