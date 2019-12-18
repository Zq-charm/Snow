package com.jon.snow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    private NumberPicker hourPicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        hourPicker=(NumberPicker) findViewById(R.id.renshu_picker);
        init();
    }

    //数字选择是可以滑动，所以需要定义一个OnValueChangeListener事件，OnScrollListener滑动事件，Formatter事件:
    //
    //Formatter事件：
    public String format(int value) {
        String tmpStr = String.valueOf(value);
        if (value < 10) {
            tmpStr = "0" + tmpStr;
        }
        return tmpStr;
    }

    // OnValueChangeListener事件:
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Toast.makeText(
                this,
                "原来的值 " + oldVal + "--新值: "
                        + newVal, Toast.LENGTH_SHORT).show();
    }

    //OnScrollListener滑动事件，滑动事件有三个状态:
    //
    //SCROLL_STATE_FLING:手离开之后还在滑动
    //
    //SCROLL_STATE_IDLE：不滑动
    //
    //SCROLL_STATE_TOUCH_SCROLL:滑动中
    public void onScrollStateChange(NumberPicker view, int scrollState) {
        switch (scrollState) {
            case NumberPicker.OnScrollListener.SCROLL_STATE_FLING:
                Toast.makeText(this, "后续滑动(飞呀飞，根本停下来)", Toast.LENGTH_LONG)
                        .show();
                break;
            case NumberPicker.OnScrollListener.SCROLL_STATE_IDLE:
                Toast.makeText(this, "不滑动", Toast.LENGTH_LONG).show();
                break;
            case NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                Toast.makeText(this, "滑动中", Toast.LENGTH_LONG)
                        .show();
                break;
        }
    }

    private void init() {
        hourPicker.setFormatter(this::format);
        hourPicker.setOnValueChangedListener(this::onValueChange);
        hourPicker.setOnScrollListener(this::onScrollStateChange);
        hourPicker.setMaxValue(99);
        hourPicker.setMinValue(1);
        hourPicker.setValue(9);

    }
}
