package com.example.asus.testviewpager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.asus.testviewpager.Password.Retrievehepassword;

import com.example.asus.testviewpager.utils.Base;
import com.example.asus.testviewpager.utils.CountDownTimerUtils;

/**
 * Created by asus on 2017/6/5.
 */

public class registeActivity extends AppCompatActivity{
    private Button mButton;

    /**
     * 注册
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);
        final EditText editText = (EditText) findViewById(R.id.activity_register_phone_edt);
        //验证码按钮
        mButton = (Button) findViewById(R.id.activity_register_send_code_btn);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rname = editText.getText().toString();
                if (rname.length()!=11 || rname.isEmpty()) {
                    new AlertDialog.Builder(registeActivity.this).setView(R.layout.registe_message_verify_user1).show();
                }else{
                    CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(mButton, 6000, 1000);
                    mCountDownTimerUtils.start();
                }
            }
        });

        //注册按钮
        Button but1= (Button) findViewById(R.id.activity_register_commit_btn);
        String str1="";
        EditText editText1 = (EditText) findViewById(R.id.activity_register_phone_edt);
        str1 = editText1.getText().toString();
        if (str1.isEmpty() ){//判断用户名是否为空
            but1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(registeActivity.this).setView(R.layout.registe_message_verify_user).show();
                }
            });
        }
        //注册按钮
        Button bnt1 = (Button) findViewById(R.id.activity_register_commit_btn);
        bnt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(registeActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });



    }
    //隐藏键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


}
