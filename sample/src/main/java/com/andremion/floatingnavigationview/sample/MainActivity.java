package com.andremion.floatingnavigationview.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.andremion.floatingnavigationview.FloatingNavigationView;

import Service.ServiceMusic;

public class MainActivity extends AppCompatActivity {

    public FloatingNavigationView mFloatingNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startMain();

        // Tìm id của floatingnavigationview
        mFloatingNavigationView = (FloatingNavigationView) findViewById(R.id.floating_navigation_view);
        //Sự kiện onclick
        mFloatingNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFloatingNavigationView.open();
            }
        });

        // Sự kiện click vào item và chuyển giao diện
        mFloatingNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    if(item.getTitle().equals("Nghe nhạc"))
                    {
                        Intent intent =new Intent(MainActivity.this,PlayMusic.class);
                        PlayMusic play =new PlayMusic();
                        play.mFloatingNavigationView = mFloatingNavigationView;
                        startActivity(intent);
                        finish();

                    }
                    else if(item.getTitle().equals("Đuổi hình bắt chữ"))
                    {
                        Intent intent = new Intent(MainActivity.this, MainCatchWord.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(item.getTitle().equals("Danh sách bài hát"))
                    {
                        Intent intent =new Intent(MainActivity.this,ListMusic.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(item.getTitle().equals("Bài hát yêu thích"))
                    {
                        Intent intent =new Intent(MainActivity.this,ListLoveMusic.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(item.getTitle().equals("Dự báo thời tiết"))
                    {
                        Intent intent =new Intent(MainActivity.this,WeatherActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    mFloatingNavigationView.close();
                    return true;
                }

        });

    }

    private void startMain() {
        Button start =(Button)findViewById(R.id.start);
        Button setting=(Button)findViewById(R.id.setting);
        ImageView image =(ImageView)findViewById(R.id.image);
        Animation animation= AnimationUtils.loadAnimation(this, R.anim.time_image);
        image.startAnimation(animation);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,ListQuestion.class);
                startActivity(intent);
                finish();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,PlayMilion.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mFloatingNavigationView.isOpened()) {
            mFloatingNavigationView.close();
        } else {
            super.onBackPressed();
        }
    }

}
