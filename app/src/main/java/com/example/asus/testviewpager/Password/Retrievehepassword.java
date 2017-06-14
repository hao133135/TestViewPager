package com.example.asus.testviewpager.Password;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.asus.testviewpager.R;
import com.example.asus.testviewpager.utils.CountDownTimerUtils;



import org.apache.http.HttpEntity;
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

public class Retrievehepassword extends AppCompatActivity {
    private Button mButton, progressBtn, button, tureButton;
    private String phoneText;
    private HttpClient client;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_retrievehepassword);


        mButton = (Button) findViewById(R.id.activity_retrieve_send_code_btn);//获取验证码按钮
        button = (Button) findViewById(R.id.activity_register_commit_btn_login);//找回密码页面中的“下一步”
        final EditText edt1 = (EditText) findViewById(R.id.activity_retrieve_phone_edt);//获取用户输入的手机号码
        //final EditText codeedit = (EditText) findViewById(R.id.activity_retrieve_verification_code_edt);//获取找回密码页面验证码输入框
        //progressBtn = (Button) findViewById(R.id.dialog_login_new_input_btn);
        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Retrievehepassword.this, Login_reset_newpassword.class);
                startActivity(i);
            }
        });*/
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneText=edt1.getText().toString();
                final CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(mButton, 60000, 1000);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //用HttpClient发送请求，分为五步
                        //第一步：创建HttpClient对象
                        HttpClient httpClient = new DefaultHttpClient();
                        //第二步：创建代表请求的对象,参数是访问的服务器地址
                        HttpGet httpGet = new HttpGet("http://39.108.73.207/jyb_cp/message/sendPwdCode?phone="+phoneText);
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
                            String message=jsonObject.getString("message");
                            //String date=jsonObject.getString("date");
                            if(state==1){
                                mCountDownTimerUtils.start();
                            }else if(message.equals("手机号码格式不正确")&&state==0){
                                Looper.prepare();
                                customDialog();
                                Looper.loop();
                            }else if(message.equals("验证码发送失败")&&state==0){
                                /*Looper.prepare();
                                customDialog();
                                Looper.loop();*/
                                mCountDownTimerUtils.start();
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
        /*mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里需要通过后台查询是否有当前电话号码
                phoneText = edt1.getText().toString();
                final CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(mButton, 60000, 1000);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //用HttpClient发送请求，分为五步
                        //第一步：创建HttpClient对象
                        client = new DefaultHttpClient();
                        //第二步：创建代表请求的对象,参数是访问的服务器地址
                        HttpGet httpGet = new HttpGet("http://39.108.73.207/jyb_cp/message/sendPwdCode?phone="+phoneText);
                        try {
                            //第三步：执行请求，获取服务器发还的相应对象
                            //第四步：检查相应的状态是否正常：检查状态码的值是200表示正常
                  *//* if(httpResponse.getStatusLine().getStatusCode()==200){
                            //第五步：从相应对象当中取出数据，放到entity当中
                            HttpEntity entity = httpResponse.getEntity();//将entity当中的数据转换为字符串
                            String response = EntityUtils.toString(entity,"utf-8");
                            //在子线程中将Message对象发出去
                            Message message = new Message();
                            message.what = SHOW_RESPONSE;
                            message.obj = response.toString();
                            handler.sendMessage(message);
                        }*//*
                            HttpResponse httpResponse = client.execute(httpGet);
                            String key = EntityUtils.toString(httpResponse.getEntity());
                            JSONObject jsonObject = new JSONObject(key);
                           int state = jsonObject.getInt("state");
                            String message=jsonObject.getString("message");
                            *//*String date=jsonObject.getString("date");*//*
                            if(state==1){
                                mCountDownTimerUtils.start();
                            }else if(message.equals("手机号码格式不正确")&&state==0){
                                Looper.prepare();
                                customDialog();
                                Looper.loop();
                            }else if(message.equals("发送验证码失败")&&state==0){
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
        });*/




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneText = edt1.getText().toString();
                Intent intent=new Intent(Retrievehepassword.this,Login_reset_newpassword.class);
                intent.putExtra("phone",phoneText);
                startActivity(intent);
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

    //设置手机号不存在提示窗口
    public  void customDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("错误提示");
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.password_nophone);
        Button dialog_but = (Button) dialog.findViewById(R.id.dialog_login_new_input_btn);
        dialog_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    //设置验证码不正确提示窗口
    public void codeDialog( ) {
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_codeerror);
        Button dialog_but = (Button) dialog.findViewById(R.id.dialog_password_codeerror);
        dialog_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

}
