package com.andremion.floatingnavigationview.sample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.andremion.floatingnavigationview.FloatingNavigationView;

import java.util.ArrayList;

import Adapter.MyArrayAdapter;
import Adapter.SongLoveDataAdapter;
import Adapter.SwipeController;
import Adapter.SwipeControllerActions;
import Entity.Song;
import Service.ServiceMusic;

public class ListLoveMusic extends Activity {

    Song song = null;
    ArrayList<Song> arr = new ArrayList<Song>();
    Button btn;
    private SongLoveDataAdapter mAdapter;
    public FloatingNavigationView mFloatingNavigationView;
    SwipeController swipeController = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listlove);
        final ServiceMusic mDB = new ServiceMusic(getApplicationContext());
        arr = mDB.getListLoveSong();
        RecyclerView lv = (RecyclerView)findViewById(R.id.recyclerView);
        mAdapter= new SongLoveDataAdapter(arr);
        lv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lv.setAdapter(mAdapter);

        swipeController = new SwipeController(getApplication(),new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                Song song =arr.get(position);
                song.setLike(false);
                if (mDB.Update(song) != -1)
                {
                    mAdapter.songs.remove(position);
                    mAdapter.notifyItemRemoved(position);
                    mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
                }
                else {
                    Toast.makeText(ListLoveMusic.this,"Không thực hiện được chức năng này",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLeftClicked(int position) {
                Intent intent = new Intent(ListLoveMusic.this, PlayMusic.class);
                intent.putExtra("vt",arr.get(position).getId());
                startActivity(intent);

                finish();

            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(lv);

        lv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
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
                if (item.getTitle().equals("Đuổi hình bắt chữ")) {
                    Intent intent = new Intent(ListLoveMusic.this, MainCatchWord.class);
                    startActivity(intent);
                    finish();
                } else if (item.getTitle().equals("Ai là triệu phú")) {
                    Intent intent = new Intent(ListLoveMusic.this, MainActivity.class);
                    MainActivity main = new MainActivity();
                    main.mFloatingNavigationView = mFloatingNavigationView;
                    startActivity(intent);
                    finish();
                } else if (item.getTitle().equals("Nghe nhạc")) {
                    Intent intent = new Intent(ListLoveMusic.this, PlayMusic.class);
                    startActivity(intent);
                    finish();
                } else if (item.getTitle().equals("Danh sách bài hát")) {
                    Intent intent = new Intent(ListLoveMusic.this, ListMusic.class);
                    startActivity(intent);
                    finish();
                }
                else if(item.getTitle().equals("Dự báo thời tiết"))
                {
                    Intent intent =new Intent(ListLoveMusic.this,WeatherActivity.class);
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
