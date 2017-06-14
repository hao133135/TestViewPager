package com.example.asus.testviewpager.utils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.asus.testviewpager.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/6/13.
 */

@SuppressLint("HandlerLeak")
public class Thread extends AppCompatActivity {
    Button btn;
    // 总时间
    long totalTime;
    // 剩余时间
    long remainingTime;
    // 定时器
    Timer timer;
    // 定时器任务
    Task task;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (remainingTime <= 0) {
                // 取消定时器所有预定的任务
                timer.cancel();
                task.cancel();
                btn.setText("获取验证码");
                btn.setClickable(true);
                return;
            }
            btn.setText((remainingTime / 1000) + "秒后可重发");
            btn.setBackgroundResource(R.drawable.bg_identify_code_normal);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);
        btn = (Button) findViewById(R.id.activity_register_send_code_btn);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                remainingTime = 10000;
                timer = new Timer();
                task = new Task();
                // 执行任务
                timer.schedule(task, 0, 1000);
                btn.setClickable(false);


            }
        });

    }

    /**
     * 定时器任务 会开启一个工作线程
     */
    class Task extends TimerTask {

        @Override
        public void run() {
            remainingTime = remainingTime - 1000;
            // 发送消息通过主线程更新UI
            handler.sendEmptyMessage(0);
        }
    }
}
