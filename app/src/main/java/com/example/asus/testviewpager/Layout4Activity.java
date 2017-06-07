package com.example.asus.testviewpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by asus on 2017/6/5.
 */

public class Layout4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout4);
        Button btn1= (Button) findViewById(R.id.wellcome);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Layout4Activity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}