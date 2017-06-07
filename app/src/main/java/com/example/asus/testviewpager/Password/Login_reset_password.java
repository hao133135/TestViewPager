package com.example.asus.testviewpager.Password;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.asus.testviewpager.R;

/**
 * Created by asus on 2017/6/6.
 */

public class Login_reset_password extends AppCompatActivity {

    private LinearLayout mainLayout;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_reset_password);
           EditText edp1= (EditText) findViewById(R.id.activity_reset_pwd_first_edt);
            String password1=edp1.getText().toString();
            EditText edp2 = (EditText) findViewById(R.id.activity_rest_pwd_again_edt);
            String password2=edp2.getText().toString();
         Button but= (Button) findViewById(R.id.activity_reset_pwd_confirm_btn);
        if(password1.equals(password2)){
            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                mainLayout= (LinearLayout) findViewById(R.id.Login_reset_password);
                    LayoutInflater inflater=LayoutInflater.from(Login_reset_password.this);
                    View PwdTwo=inflater.inflate(R.layout.password_two,null);
                    mainLayout.addView(PwdTwo);//页面是反的。需要更改
                }
            });

        }

    }
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
