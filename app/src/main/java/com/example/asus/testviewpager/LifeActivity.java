package com.example.asus.testviewpager;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.asus.testviewpager.fragment.CoachFragment;
import com.example.asus.testviewpager.fragment.LifeFragment;
import com.example.asus.testviewpager.fragment.MeFragment;
import com.example.asus.testviewpager.fragment.MessageFragment;
import com.example.asus.testviewpager.fragment.StudentFragment;


/**
 * Created by Administrator on 2017/6/5.
 */

public class LifeActivity extends AppCompatActivity implements View.OnClickListener {
    private int role=1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        Button btn_life = (Button) findViewById(R.id.activity_main_bottom_life_btn);
        Button btn_coach = (Button) findViewById(R.id.activity_main_bottom_diving_btn);
        Button btn_student = (Button) findViewById(R.id.activity_main_bottom_diving_btn);
        Button btn_message = (Button) findViewById(R.id.activity_main_bottom_message_btn);
        Button btn_me = (Button) findViewById(R.id.activity_main_bottom_me_btn);
        btn_life.setOnClickListener(this);
        btn_coach.setOnClickListener(this);
        btn_student.setOnClickListener(this);
        btn_message.setOnClickListener(this);
        btn_me.setOnClickListener(this);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_main,new LifeFragment());
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (v.getId()){
            case R.id.activity_main_bottom_life_btn:
                transaction.replace(R.id.activity_main,new LifeFragment());
                break;
            case R.id.activity_main_bottom_diving_btn:
                if(role==0){
                        transaction.replace(R.id.activity_main,new CoachFragment());
                }else{
                        transaction.replace(R.id.activity_main,new StudentFragment());
                }
                break;
            case R.id.activity_main_bottom_message_btn:
                transaction.replace(R.id.activity_main,new MessageFragment());
                break;
            case R.id.activity_main_bottom_me_btn:
                transaction.replace(R.id.activity_main,new MeFragment());
                break;
        }
        transaction.commit();
    }
}
