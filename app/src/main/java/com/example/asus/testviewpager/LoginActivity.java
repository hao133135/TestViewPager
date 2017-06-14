package com.example.asus.testviewpager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.asus.testviewpager.Bean.LoginBean;
import com.example.asus.testviewpager.Password.Retrievehepassword;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by asus on 2017/6/5.
 */

public class LoginActivity extends AppCompatActivity {
    //用户信息
    LoginBean loginBean = new LoginBean();
  /*  private String phone;
    private String password;*/
    /**
     * 登陆界面
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //跳转注册界面
       TextView tex= (TextView) findViewById(R.id.activity_login_register_tv);
        tex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,registeActivity.class);
                startActivity(i);
            }
        });
        //跳转修改密码
       TextView rpwd= (TextView) findViewById(R.id.activity_login_forget_password_tv);
        rpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(LoginActivity.this,Retrievehepassword.class);
                startActivity(i);
            }
        });
        loginBean.setPhone("123");//需要从后台获取数据
        loginBean.setPassword("123");//需要从后台获取数据

        //跳转生活界面

        //获取登录界面phone和密码
        //登陆按钮监听
        /*Button login = (Button) findViewById(R.id.activity_login_commit_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog(v);
            }
        });*/
       Button btn1 = (Button) findViewById(R.id.activity_login_commit_btn);
       btn1.setOnClickListener(new View.OnClickListener() {
           EditText editPhone = (EditText) findViewById(R.id.activity_login_user_name_edt);
           EditText editPassword = (EditText) findViewById(R.id.activity_login_user_password_edt);
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(LoginActivity.this, LifeActivity.class);
                //startActivity(i);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String phone = editPhone.getText().toString();
                        String password = editPassword.getText().toString();
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpGet httpGet = new HttpGet("http://39.108.73.207/jyb_cp/account/login?phone="+phone+"&password="+password);
                        try {
                            HttpResponse httpResponse = httpClient.execute(httpGet);
                            String key = EntityUtils.toString(httpResponse.getEntity());
                            JSONObject result = new JSONObject(key);
                            //"state": 1, "message": "登录成功", "data": {"address":"地址", "birthday":生日, "height":"身高", "id":编号, "imgpath":"头像地址", "interest":"兴趣爱好", "job":"工作", "nickname":"昵称", "password":"密码", "phone":"手机号", "role":"角色", "salary":薪水 "sex":"性别", "signature":"个性签名", "weight":"体重", "xingzuo":"星座" "region":"地区"}
                            //"state":0, "message":"用户不存在" "data":“”
                            //"state":0, "message":"密码错误" "data":“”
                            int state = result.getInt("state");
                            String message = result.getString("message");
                            if(state==1){
                                Intent i = new Intent(LoginActivity.this, LifeActivity.class);
                                startActivity(i);
                            }else if(state==0){
                                if(message.equals("用户不存在")){
                                    Looper.prepare();
                                    customDialogPhone();
                                    Looper.loop();
                                }else if(message.equals("密码错误")){
                                    Looper.prepare();
                                    customDialogPassword();
                                    Looper.loop();
                                }
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

    //用户名不存在
    public void customDialogPhone(){
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.login_message_phone);
        Button dialog_but = (Button) dialog.findViewById(R.id.dialog_login_new_input_btn);
        ImageButton dialog_image = (ImageButton) dialog.findViewById(R.id.dialog_login_close_user_error);
        dialog_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    //密码错误
    public void customDialogPassword(){
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.login_message_password);
        Button dialog_but = (Button) dialog.findViewById(R.id.dialog_login_new_input_btn);
        ImageButton dialog_image = (ImageButton) dialog.findViewById(R.id.dialog_login_close_password_error);
        dialog_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    /**
     * 隐藏键盘
     * @param ev
     * @return
     */
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
