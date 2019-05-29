package com.andremion.floatingnavigationview.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.floatingnavigationview.FloatingNavigationView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Adapter.MyArrayAdapter;
import Entity.Song;
import Service.ServiceMusic;

public class ListMusic extends Activity {

    Song song = null;
    ArrayList<Song> arr = new ArrayList<Song>();
    AutoCompleteTextView text;
    MyArrayAdapter mayArr;
    TextView title;
    ListView lv =null;
    public FloatingNavigationView mFloatingNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listmusic);
        text=(AutoCompleteTextView)findViewById(R.id.searchtext);
        ServiceMusic mDB = new ServiceMusic(getApplicationContext());
        title=(TextView)findViewById(R.id.titlelist);
       // mDB.onUpgrade(mDB.getReadableDatabase(),0,1);
        lv =(ListView)findViewById(R.id.listview);
        arr= mDB.getAllSong();

        // Tạo danh sách list gợi ý
        Set<String> set = new HashSet<String>();
        for(int i=0;i<arr.size();i++)
        {
            Song a= arr.get(i);
            set.add(a.getTitle());
            set.add(a.getSinger());
        }
        List<String>listsong = new ArrayList<>();
        for (String str : set) {
            listsong.add(str);
        }

        ArrayAdapter adapterSong = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listsong);

        text.setAdapter(adapterSong);


        // Sét đặt số ký tự nhỏ nhất, để cửa sổ gợi ý hiển thị
        text.setThreshold(1);
        mayArr = new MyArrayAdapter(this, R.layout.item, arr);
        lv.setAdapter(mayArr);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("12345567","loi");
                Intent intent = new Intent(ListMusic.this, PlayMusic.class);
                intent.putExtra("vt",mayArr.arr.get(i).getId());
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
                if(item.getTitle().equals("Đuổi hình bắt chữ"))
                {
                    Intent intent = new Intent(ListMusic.this, MainCatchWord.class);
                    startActivity(intent);
                    finish();
                }
                else if(item.getTitle().equals("Ai là triệu phú"))
                {
                    Intent intent =new Intent(ListMusic.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(item.getTitle().equals("Nghe nhạc"))
                {
                    Intent intent =new Intent(ListMusic.this,PlayMusic.class);
                    startActivity(intent);
                    finish();
                }
                else if(item.getTitle().equals("Bài hát yêu thích"))
                {
                    Intent intent =new Intent(ListMusic.this,ListLoveMusic.class);
                    startActivity(intent);
                    finish();
                }
                else if(item.getTitle().equals("Dự báo thời tiết"))
                {
                    Intent intent =new Intent(ListMusic.this,WeatherActivity.class);
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
    public void search(View view) {
        String sing = text.getText()+"";
        if(sing=="")
        {
            Toast.makeText(ListMusic.this,"Bạn chưa nhập gì!"+"\n"+"Mời nhập lại!",Toast.LENGTH_LONG).show();
        }
        else {
            ArrayList <Song> arr1 = new ArrayList<Song>();
            for(int i=0;i<arr.size();i++)
            {
                if((sing.toLowerCase().equals(arr.get(i).getSinger().toLowerCase()))||(sing.toLowerCase().equals(arr.get(i).getTitle().toLowerCase())))
                {
                    arr1.add(arr.get(i));
                }
            }

            if (arr1.size() == 0) {
                title.setText("Không tìm thấy " + sing);
            } else {
                title.setText("Kết quả cho " + sing + " :");
            }
            mayArr = new MyArrayAdapter(ListMusic.this, R.layout.item, arr1);
            lv.setAdapter(mayArr);
        }
    }
}
