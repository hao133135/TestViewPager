package com.example.asus.testviewpager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.graphics.Matrix;
import android.widget.ImageView;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class MainActivity extends Activity {

    private ViewPager viewPager;
    private ImageView imageView;
    private List<View> lists = new ArrayList<View>();
    private MyAdapter myAdapter;
    private Bitmap cursor;
    private int offSet;
    private int currentItem;
    private Matrix matrix = new Matrix();
    private int bmWidth;
    private Animation animation;
    /*private TextView textView1;
    private TextView textView2;
    private TextView textView3;
*/

    /**
     * 引导页面
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* setContentView(R.layout.layout4);
        Button but1= (Button) findViewById(R.id.wellcome);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });*/


        lists.add(getLayoutInflater().inflate(R.layout.layout1, null));
        lists.add(getLayoutInflater().inflate(R.layout.layout2, null));
        lists.add(getLayoutInflater().inflate(R.layout.layout3, null));
        lists.add(getLayoutInflater().inflate(R.layout.layout4, null));


        myAdapter = new MyAdapter(lists);

        viewPager = (ViewPager) findViewById (R.id.viewPager);
        viewPager.setAdapter(myAdapter);




       /* textView3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                viewPager.setCurrentItem(2);
            }
        });*/
    }

}
