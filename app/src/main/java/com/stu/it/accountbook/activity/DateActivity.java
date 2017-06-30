package com.stu.it.accountbook.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.stu.it.accountbook.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by Touch on 2017/5/13.
 */
public class DateActivity extends BaseActivity {
    private static int result=1001;
    private com.prolificinteractive.materialcalendarview.MaterialCalendarView mCalendarView;
    private Button mDate_Button;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public int getLayoutResID() {
        return R.layout.date_activity;
    }

    @Override
    public void initView() {
        mDate_Button= (Button) findViewById(R.id.date_button);
        mCalendarView= (MaterialCalendarView) findViewById(R.id.calendarView);
        mCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2016, 1, 1))
                .setMaximumDate(CalendarDay.from(2017, 12, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initListener() {
        mDate_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectTime = sdf.format(Calendar.getInstance().getTime());
                if(mCalendarView.getSelectedDate()!=null){
                    selectTime = sdf.format(mCalendarView.getSelectedDate().getDate());
                }
                Intent intent = new Intent();
                intent.putExtra("result_date", selectTime);
                /*
                 * 调用setResult方法表示我将Intent对象返回给之前的那个Activity，这样就可以在onActivityResult方法中得到Intent对象，
                 */
                setResult(1001, intent);
                //    结束当前这个Activity对象的生命
                finish();
            }
        });
    }
}
