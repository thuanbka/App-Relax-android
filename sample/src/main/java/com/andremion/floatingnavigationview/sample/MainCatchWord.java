package com.andremion.floatingnavigationview.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.andremion.floatingnavigationview.FloatingNavigationView;

import Service.ServiceMusic;

public class MainCatchWord extends AppCompatActivity {
    public FloatingNavigationView mFloatingNavigationView;
    ServiceMusic nDB;
    Button con,restart;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maincatchword);
        con =(Button)findViewById(R.id.continues);
        restart=(Button)findViewById(R.id.start);
        nDB=new ServiceMusic(getApplicationContext());
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent =new Intent(MainCatchWord.this,CatchWord.class);
               startActivity(intent);
                finish();
            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nDB.UpdateWord(1,0) != -1)
                    Log.d("tag","Success");
                else Log.d("tag","Fail");
                Intent intent =new Intent(MainCatchWord.this,CatchWord.class);
                startActivity(intent);
                finish();
            }
        });
        mFloatingNavigationView = (FloatingNavigationView) findViewById(R.id.floating_navigation_view);
        mFloatingNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFloatingNavigationView.open();
            }
        });
        mFloatingNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(item.getTitle().equals("Danh sách bài hát"))
                {
                    Intent intent = new Intent(MainCatchWord.this, ListMusic.class);
                    startActivity(intent);
                    finish();
                }
                else if(item.getTitle().equals("Ai là triệu phú"))
                {
                    Intent intent =new Intent(MainCatchWord.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(item.getTitle().equals("Nghe nhạc"))
                {
                    Intent intent =new Intent(MainCatchWord.this,PlayMusic.class);
                    startActivity(intent);
                    finish();
                }
                else if(item.getTitle().equals("Bài hát yêu thích"))
                {
                    Intent intent =new Intent(MainCatchWord.this,ListLoveMusic.class);
                    startActivity(intent);
                    finish();
                }
                else if(item.getTitle().equals("Dự báo thời tiết"))
                {
                    Intent intent =new Intent(MainCatchWord.this,WeatherActivity.class);
                    startActivity(intent);
                    finish();
                }

                mFloatingNavigationView.close();
                return true;
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
