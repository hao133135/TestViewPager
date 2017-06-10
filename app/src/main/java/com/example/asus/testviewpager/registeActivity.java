package com.example.asus.testviewpager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.asus.testviewpager.Bean.LoginBean;
import com.example.asus.testviewpager.Password.Retrievehepassword;

import com.example.asus.testviewpager.utils.Base;
import com.example.asus.testviewpager.utils.CountDownTimerUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by asus on 2017/6/5.
 */

public class registeActivity extends AppCompatActivity {
    private Button mButton;
    private LoginBean bean = new LoginBean();
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
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

        //获取用户名输入信息
        final EditText editText1 = (EditText) findViewById(R.id.activity_register_phone_edt);
        //获取密码信息
        final EditText password = (EditText) findViewById(R.id.activity_register_password_edt);
        //获取验证码信息
        final EditText verify = (EditText) findViewById(R.id.activity_register_verification_code_edt);
        //获取注册类型


        //注册按钮
        Button but1= (Button) findViewById(R.id.activity_register_commit_btn);
        but1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String rname = editText1.getText().toString();
                    String rverify = verify.getText().toString();
                    String mrverify = "1234";//需要从后台获取验证码
                    String rpassword = password.getText().toString();
                    registeActivity.this.bean.setPhone("12345678910");//需要从后台获取用户名
                    if(rname.isEmpty()||rpassword.isEmpty()||rverify.isEmpty()){//判断密码
                        customDialog_information(v);
                    }else if (rname.length()!=11 ){//判断用户名长度不为11
                        customDialog_error(v);
                    }else if(rname.equals(registeActivity.this.bean.getPhone())){//判断用户是否已注册
                        customDialog_registered(v);
                    }else if(rverify.equals(mrverify)){
                        Intent i =new Intent(registeActivity.this,LoginActivity.class);
                        startActivity(i);
                    }else{
                        customDialog_verify(v);
                    }
                }
            });
    }

    public void customDialog_error(View view){
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.registe_message_verify_user);
        Button dialog_but = (Button) dialog.findViewById(R.id.dialog_login_new_input_btn);
        dialog_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void customDialog_registered(View view){
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.registe_message_verify_user1);
        Button dialog_but = (Button) dialog.findViewById(R.id.dialog_login_new_input_btn_1);
        Button dialog_but1 = (Button) dialog.findViewById(R.id.dialog_login_new_input_btn_5);
        dialog_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog_but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(registeActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        dialog.show();
    }
    public void customDialog_verify(View view){
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.registe_massage_verify_error);
        Button dialog_but = (Button) dialog.findViewById(R.id.dialog_login_new_input_btn_3);
        dialog_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void customDialog_information(View view){
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.registe_massage_verify_password);
        Button dialog_but = (Button) dialog.findViewById(R.id.dialog_login_new_input_btn_2);
        dialog_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
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
