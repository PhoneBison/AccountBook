package com.stu.it.accountbook.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.stu.it.accountbook.R;
import com.stu.it.accountbook.utils.UIUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import static com.stu.it.accountbook.R.id.timePicker;

/**
 * Created by Touch on 2017/5/13.
 */
public class TimeActivity extends BaseActivity{
    private TimePicker mTime_Choose;
    private Button mTime;
    private String selectTime;
    static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    @Override
    public int getLayoutResID() {
        return R.layout.time_activity;
    }

    @Override
    public void initView() {
        mTime_Choose= (TimePicker) findViewById(timePicker);
        mTime= (Button) findViewById(R.id.time_button);
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        mTime_Choose.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
        mTime_Choose.setCurrentMinute(c.get(Calendar.MINUTE));
        System.out.println(c.get(Calendar.HOUR_OF_DAY));
        System.out.println(c.get(Calendar.MINUTE));
//        mTime_Choose.setCurrentHour(10);
//        mTime_Choose.setCurrentMinute(40);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initListener() {
        //设置改变时间时的监听事件
        mTime_Choose.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                selectTime=hourOfDay+"："+minute+"";
                Toast.makeText(UIUtils.getContext(),selectTime,Toast.LENGTH_SHORT).show();
            }
        });

        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String result_time = sdf.format(selectTime);
                String result_time = mTime_Choose.getCurrentHour()+":"+mTime_Choose.getCurrentMinute();
                Intent intent = new Intent();
                intent.putExtra("result_time", result_time);
                /*
                 * 调用setResult方法表示我将Intent对象返回给之前的那个Activity，这样就可以在onActivityResult方法中得到Intent对象，
                 */
                setResult(1003, intent);
                //    结束当前这个Activity对象的生命
                finish();
            }
        });

    }

}
