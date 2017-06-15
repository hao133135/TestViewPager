package com.example.asus.testviewpager.Password;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.testviewpager.LoginActivity;
import com.example.asus.testviewpager.R;
import com.example.asus.testviewpager.registeActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by asus on 2017/6/6.
 */

public class Login_reset_newpassword extends AppCompatActivity {
    private Context context=this;

    //private LinearLayout mainLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_reset_newpassword);
        final EditText edp1 = (EditText) findViewById(R.id.activity_reset_pwd_first_edt);
           /* String password1=edp1.getText().toString();*/
        final EditText edp2 = (EditText) findViewById(R.id.activity_rest_pwd_again_edt);
           /* String password2=edp2.getText().toString();*/
           final EditText codeedi = (EditText) findViewById(R.id.code);
        Button but = (Button) findViewById(R.id.activity_reset_pwd_confirm_btn);//设定新密码页面中确认按钮
       /* if(password1.equals(password2)){*/
       Intent intent = this.getIntent();
        final String phone = intent.getStringExtra("phone");
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String password1 = edp1.getText().toString();//第一次密码
                final String password2 = edp2.getText().toString();//第二次密码
                final String code = codeedi.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        //http://39.108.73.207
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost("HTTP://192.168.0.108:8080/jyb_cp/account/resetPwd");
                        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
                        try {
                            JSONObject param = new JSONObject();
                            param.put("phone",phone);
                            param.put("newPassword",password1);
                            param.put("verCode",code);
                            StringEntity se = new StringEntity(param.toString());
                            if(password1.equals(password2)){
                                httpPost.setEntity(se);
                                HttpResponse httpResponse = httpClient.execute(httpPost);
                                String key = EntityUtils.toString(httpResponse.getEntity());
                                JSONObject jsonObject = new JSONObject(key);
                                int state = jsonObject.getInt("state");
                                String message=jsonObject.getString("message");
                                //String date=jsonObject.getString("date");
                                /*"state":1 "message":"成功修改密码", "data":""
                                "state":0 "message":"验证码为空", "data":""
                                "state":0 "message":"验证码错误", "data":""
                                "state":0 "message":"验证码失效", "data":""
                                "state":0 "message":"修改密码失败", "data":""*/
                                if(state==1){
                                    Intent i = new Intent(Login_reset_newpassword.this, LoginActivity.class);
                                    startActivity(i);
                                }else if(state==0){
                                    if(message.equals("验证码为空")){
                                        Looper.prepare();
                                        verify_empty();
                                        Looper.loop();
                                    }else if(message.equals("验证码错误")){
                                        Looper.prepare();
                                        verify_error();
                                        Looper.loop();
                                    }else if(message.equals("验证码失效")){
                                        Looper.prepare();
                                        verify_lose();
                                        Looper.loop();
                                    }else if(message.equals("修改密码失败")){
                                        Looper.prepare();
                                        password_defeated();
                                        Looper.loop();
                                    }else if(message.equals("密码为空")){
                                        Looper.prepare();
                                        password_defeated();
                                        Looper.loop();
                                    }
                                }
                            }else{
                                Looper.prepare();
                                customDialog();
                                Looper.loop();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });

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

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
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
//设置密码不一致弹窗
    public void customDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.password_two);
        Button dialog_but = (Button) dialog.findViewById(R.id.dialog_login_new_input_btn);
        dialog_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void verify_empty(){
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.password_two);
        Button dialog_but = (Button) dialog.findViewById(R.id.dialog_login_new_input_btn);
        dialog_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void verify_error(){
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.password_two);
        Button dialog_but = (Button) dialog.findViewById(R.id.dialog_login_new_input_btn);
        dialog_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void verify_lose(){
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.password_two);
        Button dialog_but = (Button) dialog.findViewById(R.id.dialog_login_new_input_btn);
        dialog_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void password_defeated(){
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);

        dialog.setContentView(R.layout.password_two);
        Button dialog_but = (Button) dialog.findViewById(R.id.dialog_login_new_input_btn);
        dialog_but.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
