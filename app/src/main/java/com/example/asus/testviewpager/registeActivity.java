package com.example.asus.testviewpager;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;

import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;


import com.example.asus.testviewpager.Bean.LoginBean;
import com.example.asus.testviewpager.utils.CountDownTimerUtils;
import com.example.asus.testviewpager.utils.registePost;

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
import java.util.Timer;
import java.util.TimerTask;

import static android.R.id.message;

/**
 * Created by asus on 2017/6/5.
 */

public class registeActivity extends AppCompatActivity {
    private Button mButton;
    private LoginBean bean = new LoginBean();
    private View view;
    private boolean state;
    private String sss;
    private String address = "http://39.108.73.207/jyb_cp/account/regist";
    private int role = 3;
    private registePost post = new registePost();



    /**
     * 注册
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);
        registeActivity.this.bean.setRole(3);
        final RadioButton mButton1 = (RadioButton) findViewById(R.id.activity_register_type_student_rbt);//设置学员按钮
        final RadioButton mButton2 = (RadioButton) findViewById(R.id.activity_register_teacher_rbt);//设置教练按钮
        final RadioButton mButton3 = (RadioButton) findViewById(R.id.activity_register_personage_rbt);//设置个人按钮
        mButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    registeActivity.this.bean.setRole(0);
                }
            }
        });
        mButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    registeActivity.this.bean.setRole(1);
                }
            }
        });
        mButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    registeActivity.this.bean.setRole(2);
                }
            }
        });
        final EditText editText = (EditText) findViewById(R.id.activity_register_phone_edt);
        //验证码按钮
        mButton = (Button) findViewById(R.id.activity_register_send_code_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            //获取注册用户名
            @Override
            public void onClick(View v) {
                final String rname = editText.getText().toString();
                final CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(mButton, 60000, 1000);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //用HttpClient发送请求，分为五步
                        //第一步：创建HttpClient对象
                        HttpClient httpClient = new DefaultHttpClient();
                        //第二步：创建代表请求的对象,参数是访问的服务器地址
                        HttpGet httpGet = new HttpGet("http://39.108.73.207/jyb_cp/message/sendRegCode?phone="+rname);

                        try {
                            //第三步：执行请求，获取服务器发还的相应对象
                            //第四步：检查相应的状态是否正常：检查状态码的值是200表示正常
                   /*if(httpResponse.getStatusLine().getStatusCode()==200){
                            //第五步：从相应对象当中取出数据，放到entity当中
                            HttpEntity entity = httpResponse.getEntity();//将entity当中的数据转换为字符串
                            String response = EntityUtils.toString(entity,"utf-8");
                            //在子线程中将Message对象发出去
                            Message message = new Message();
                            message.what = SHOW_RESPONSE;
                            message.obj = response.toString();
                            handler.sendMessage(message);
                        }*/
                            HttpResponse httpResponse = httpClient.execute(httpGet);
                            String key = EntityUtils.toString(httpResponse.getEntity());
                            JSONObject jsonObject = new JSONObject(key);
                            int state = jsonObject.getInt("state");
                            String message = jsonObject.getString("message");
                            String msg1 = "手机号格式不正确";
                               /* "state":0 "message":"手机号格式不正确", "data":""

                                "status":0 "msg":"该手机已注册", "data":""

                                "state":0 "message":"密码不能为空", "data":""

                                "state":0 "message":"密码长度过短", "data":""

                                "state":0 "message":"密码长度超限", "data":""

                                "status":0 "message":"验证码失效", "data":""

                                "state":0 "message":"验证码错误", "data":""

                                "state":0 "message":"验证码为空", "data":""*/

                            if(state==1){//成功返回状态
                                mCountDownTimerUtils.start();
                            }else if(state==0) {//验证码失败弹窗
                                if (message.equals(msg1))
                                    Looper.prepare();
                                    customDialog_error();
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

        //获取用户名输入信息
        final EditText edithone = (EditText) findViewById(R.id.activity_register_phone_edt);
        //获取密码信息
        final EditText editPassword = (EditText) findViewById(R.id.activity_register_password_edt);
        //获取验证码信息
        final EditText editVerCode = (EditText) findViewById(R.id.activity_register_verification_code_edt);



        //注册按钮
        Button btn1= (Button) findViewById(R.id.activity_register_commit_btn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = edithone.getText().toString();
                final String password = editPassword.getText().toString();
                final String verCode = editVerCode.getText().toString();
                final String url = "http://39.108.73.207/jyb_cp/account/regist";
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //HttpPost request = new HttpPost(url);
                        //request.setHeader("Content-Type", "application/x-www-form-urlencoded");
                        // 先封装一个 JSON 对象
                        //JSONObject param = new JSONObject();
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpGet httpGet = new HttpGet("http://39.108.73.207/jyb_cp/message/sendRegCode?phone="+phone+"&password="+password+"&role="+registeActivity.this.bean.getRole()+"&verCode="+verCode);

                        try {
                            HttpResponse httpResponse = httpClient.execute(httpGet);
                            String key = EntityUtils.toString(httpResponse.getEntity());
                            /*param.put("phone", phone);
                            param.put("password", password);
                            param.put("role", registeActivity.this.bean.getRole());
                            param.put("verCode", verCode);
                            // 绑定到请求 Entry
                            StringEntity se = new StringEntity(param.toString());
                            request.setEntity(se);
                            HttpResponse httpResponse = new DefaultHttpClient().execute(request);
                            // 得到应答的字符串，这也是一个 JSON 格式保存的数据
                            String retSrc = EntityUtils.toString(httpResponse.getEntity());*/
                            // 生成 JSON 对象
                    /*"state":1"message":"注册成功","data":""
                    "state":0"message":"手机号格式不正确","data":""
                    "status":0"msg":"该手机已注册","data":""
                    "state":0"message":"密码不能为空","data":""
                    "state":0"message":"密码长度过短","data":""
                    "state":0"message":"密码长度超限","data":""
                    "status":0"message":"验证码失效","data":""
                    "state":0"message":"验证码错误","data":""
                    "state":0"message":"验证码为空","data":""*/
                            JSONObject result = new JSONObject(key);
                            int state = result.getInt("state");
                            String message = result.getString("message");
                            String msg1 = "注册成功";
                            String msg2 = "手机号格式不正确";
                            String msg3 = "该手机已注册";
                            String msg4 = "密码不能为空";
                            String msg5 = "密码长度过短";
                            String msg6 = "密码长度超限";
                            String msg7 = "验证码失效";
                            String msg8 = "验证码错误";
                            String msg9 = "验证码为空";
                            if (state == 1) {//成功返回状态
                                Intent i = new Intent(registeActivity.this, LoginActivity.class);
                                startActivity(i);
                            } else if (state == 0) {//验证码失败弹窗
                                if (message.equals(msg2)) {
                                    Looper.prepare();
                                    customDialog_error();
                                    Looper.loop();
                                } else if (message.equals(msg3)) {
                                    Looper.prepare();
                                    customDialog_error();
                                    Looper.loop();
                                } else if (message.equals(msg4)) {
                                    Looper.prepare();
                                    customDialog_error();
                                    Looper.loop();
                                } else if (message.equals(msg5)) {
                                    Looper.prepare();
                                    customDialog_error();
                                    Looper.loop();
                                } else if (message.equals(msg6)) {
                                    Looper.prepare();
                                    customDialog_error();
                                    Looper.loop();
                                } else if (message.equals(msg7)) {
                                    Looper.prepare();
                                    customDialog_error();
                                    Looper.loop();
                                } else if (message.equals(msg8)) {
                                    Looper.prepare();
                                    customDialog_error();
                                    Looper.loop();
                                } else if (message.equals(msg9)) {
                                    Looper.prepare();
                                    customDialog_error();
                                    Looper.loop();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    }).start();
            }
        });


        /*findViewById(R.id.activity_register_commit_btn).setOnClickListener(new View.OnClickListener() {
           *//* EditText edithone = (EditText) findViewById(R.id.phone);
            EditText editPassword = (EditText) findViewById(R.id.password);
            EditText editVerCode = (EditText) findViewById(R.id.verCode);*//*
            @Override
            public void onClick(View v) {
                String phone = edithone.getText().toString();
                String password = editPassword.getText().toString();
                String verCode = editVerCode.getText().toString();
                Intent i =new Intent(registeActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });*/

    }

    //用户名错误
    public void customDialog_error(){
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.registe_message_verify_user);
        Button dialog_but = (Button) dialog.findViewById(R.id.dialog_login_new_input_btn);
        ImageButton dialog_image = (ImageButton) dialog.findViewById(R.id.dialog_login_close_imageButton_user_error);
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

    //用户已注册
    public void customDialog_registered(){
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.registe_message_verify_user1);
        Button dialog_but = (Button) dialog.findViewById(R.id.dialog_login_new_input_btn_1);
        Button dialog_but1 = (Button) dialog.findViewById(R.id.dialog_login_new_input_btn_5);
        ImageButton dialog_image = (ImageButton) dialog.findViewById(R.id.dialog_login_close_imageButton_user_registered);
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
        dialog_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    //验证码错误
    public void customDialog_verify(){
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.registe_massage_verify_error);
        Button dialog_but = (Button) dialog.findViewById(R.id.dialog_login_new_input_btn_3);
        ImageButton dialog_image = (ImageButton) dialog.findViewById(R.id.dialog_login_close_imageButton_error);
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
    //信息输入不完整
    public void customDialog_information(){
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.registe_massage_verify_password);
        Button dialog_but = (Button) dialog.findViewById(R.id.dialog_login_new_input_btn_2);
        ImageButton dialog_image = (ImageButton) dialog.findViewById(R.id.dialog_login_close_imageButton_incomplete);
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
