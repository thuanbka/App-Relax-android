package com.andremion.floatingnavigationview.sample;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.floatingnavigationview.FloatingNavigationView;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import Adapter.ImageConverter;
import Entity.Song;
import Service.ServiceMusic;

public class PlayMusic extends AppCompatActivity {
    Button like;
    Intent getintent=null;
    Button pause;
    public static int vt=0;
    ProgressBar proBar;
    Button add, sub;
    ImageView image;
    TextView singer,songName,timeStart,timeEnd;
    boolean like1 =true, ispause= false;
    private Handler threadHandler = new Handler();
    private MediaPlayer mediaPlayer;
    public FloatingNavigationView mFloatingNavigationView;
    UpdateSeekBarThread updateSeekBarThread;
    Song song;
    int duration =0;
    int time =0;
    ArrayList<Song> arr=new ArrayList<Song>();
    Animation animation;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final ServiceMusic musicDB=new ServiceMusic(getApplicationContext());
        setContentView(R.layout.playmusicmain);
        arr=musicDB.getAllSong();
        like =(Button)findViewById(R.id.like);
        pause=(Button)findViewById(R.id.pause) ;
        proBar=(ProgressBar)findViewById(R.id.progressBar);
        singer=(TextView)findViewById(R.id.single) ;
        songName=(TextView)findViewById(R.id.namemusic);
        add=(Button)findViewById(R.id.add);
        sub=(Button)findViewById(R.id.sub);
        image=(ImageView)findViewById(R.id.image);
        timeEnd=(TextView)findViewById(R.id.end);
        timeStart=(TextView)findViewById(R.id.start);
        Log.d("playpusic1234","lỗi");
        getintent = this.getIntent();
        int ran =new Random().nextInt(arr.size());
        vt= (int) getintent.getIntExtra("vt",ran);
        song=arr.get(vt);
        startMusic(song);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(like1)
                {
                    song.setLike(true);
                    if (musicDB.Update(song) != -1)
                    {
                        Toast.makeText(PlayMusic.this,"Đã thêm bài hát vào danh sách thích",Toast.LENGTH_SHORT).show();
                        like.setBackgroundResource(R.drawable.iconhong);
                        like1=false;

                    }
                    else {
                        Toast.makeText(PlayMusic.this,"Không thực hiện được chức năng này",Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    song.setLike(false);
                    if (musicDB.Update(song) != -1)
                    {
                        Toast.makeText(PlayMusic.this,"Đã xóa bài hát khỏi danh sách thích",Toast.LENGTH_SHORT).show();
                        like.setBackgroundResource(R.drawable.iconden);
                        like1=true;

                    }
                    else {
                        Toast.makeText(PlayMusic.this,"Không thực hiện được chức năng này",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                if(vt==arr.size()-1)
                {
                    vt=0;
                }
                else
                {
                    vt++;
                }
                getintent = new Intent(PlayMusic.this,PlayMusic.class);
                getintent.putExtra("vt",vt);
                startActivity(getintent);
                finish();
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                if(vt==0)
                {
                    vt=arr.size()-1;
                }
                else
                {
                    vt--;
                }
                getintent = new Intent(PlayMusic.this,PlayMusic.class);
                getintent.putExtra("vt",vt);
                startActivity(getintent);
                finish();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ispause)
                {
                    animation.cancel();
                    image.clearAnimation();
                    ispause=false;
                    pause.setBackgroundResource(R.drawable.play);
                    mediaPlayer.pause();
                }
                else
                {
                    animation.start();
                    image.startAnimation(animation);
                    pause.setBackgroundResource(R.drawable.pause);
                    ispause=true;
                    mediaPlayer.start();
                }
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
                    mediaPlayer.stop();
                    updateSeekBarThread.isrun=false;
                    Intent intent = new Intent(PlayMusic.this, MainCatchWord.class);
                    startActivity(intent);
                    finish();
                }
                else if(item.getTitle().equals("Ai là triệu phú"))
                {
                    mediaPlayer.stop();
                    updateSeekBarThread.isrun=false;
                    Intent intent =new Intent(PlayMusic.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(item.getTitle().equals("Danh sách bài hát"))
                {
                    mediaPlayer.stop();
                    updateSeekBarThread.isrun=false;
                    Intent intent =new Intent(PlayMusic.this,ListMusic.class);
                    startActivity(intent);
                    finish();
                }
                else if(item.getTitle().equals("Bài hát yêu thích"))
                {
                    mediaPlayer.stop();
                    updateSeekBarThread.isrun=false;
                    Intent intent =new Intent(PlayMusic.this,ListLoveMusic.class);
                    startActivity(intent);
                    finish();
                }
                else if(item.getTitle().equals("Dự báo thời tiết"))
                {
                    Intent intent =new Intent(PlayMusic.this,WeatherActivity.class);
                    startActivity(intent);
                    finish();
                }

                mFloatingNavigationView.close();
                return true;
            }
        });
        Log.d("MainActivityLifecycle", "===== onCreate =====");
    }

    @Override
    public void onBackPressed() {
        if (mFloatingNavigationView.isOpened()) {
            mFloatingNavigationView.close();
        } else {
            super.onBackPressed();
        }
    }

    // Tìm ID của một file nguồn trong thư mục 'raw' theo tên.
    public int getRawResIdByName(String resName)  {
        String pkgName = this.getPackageName();
        // Return 0 if not found.
        // Trả về 0 nếu không tìm thấy.
        int resID = this.getResources().getIdentifier(resName, "raw", pkgName);
        return resID;
    }

    // Chuyển số lượng milli giây thành một String có ý nghĩa.
    private String millisecondsToString(int milliseconds)  {
        long minutes = TimeUnit.MILLISECONDS.toMinutes((long) milliseconds);
        long seconds =  TimeUnit.MILLISECONDS.toSeconds((long) milliseconds)%60 ;
        if(seconds<10)
        {
            return minutes+":"+ 0+seconds;
        }
        else
            return minutes+":"+seconds;

    }

    // Tua bài hát
    public void doFastForward(View view) {
        int currentPosition = this.mediaPlayer.getCurrentPosition();
        int duration = this.mediaPlayer.getDuration();
        // 5 giây.
        int ADD_TIME = 5000;
        if(currentPosition + ADD_TIME < duration)  {
            this.mediaPlayer.seekTo(currentPosition + ADD_TIME);
        }
    }

    // Lùi bài hát
    public void doRewind(View view) {
        int currentPosition = this.mediaPlayer.getCurrentPosition();
        // 5 giây.
        int SUBTRACT_TIME = 5000;
        if(currentPosition - SUBTRACT_TIME > 0 )  {
            this.mediaPlayer.seekTo(currentPosition - SUBTRACT_TIME);
        }
    }

    // Thread sử dụng để Update trạng thái cho SeekBar.
    class UpdateSeekBarThread implements Runnable {
        boolean isrun=false;
        UpdateSeekBarThread()
        {
            isrun=true;
        }

        public void run()  {
            if(this.isrun) {
                int currentPosition = PlayMusic.this.mediaPlayer.getCurrentPosition();
                PlayMusic.this.time = currentPosition;
                String currentPositionStr = millisecondsToString(currentPosition);
                timeStart.setText(currentPositionStr);
                proBar.setProgress(currentPosition);
                if (PlayMusic.this.time >= PlayMusic.this.duration - 1000) {
                    PlayMusic.this.time = 0;
                    this.isrun=false;
                    if (vt == arr.size() - 1) {
                        vt = 0;
                        Log.d("tag", "Playmusic  " + vt);
                    } else {
                        Log.d("tag", "Playmusic  " + vt);
                        vt++;
                    }
                    PlayMusic.this.mediaPlayer.stop();
                    startMusic(arr.get(vt));
                }
                // Ngừng thread 50 mili giây.
                threadHandler.postDelayed(this, 50);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ispause=false;
        mediaPlayer.pause();
        pause.setBackgroundResource(R.drawable.play);
        Log.d("MainActivityLifecycle", "===== onDestroy =====");
    }

    private void startMusic( Song song)
    {
        if(song.isLike())
        {
            like.setBackgroundResource(R.drawable.iconhong);
            like1=false;
        }
        else
        {
            like.setBackgroundResource(R.drawable.iconden);
            like1=true;
        }
        singer.setText(song.getSinger());
        songName.setText(song.getTitle());
        Resources res = getResources();
        String mDrawableName = song.getIcon();
        int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
        // Drawable drawable = res.getDrawable(resID );
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), resID);
        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);
        image.setImageBitmap(circularBitmap);
        animation= AnimationUtils.loadAnimation(this, R.anim.time_image);
        image.startAnimation(animation);
        // ID của file nhạc trong thư mục 'raw'.
        int songId = this.getRawResIdByName("m"+song.getId());
        // Tạo đối tượng MediaPlayer.
        this.mediaPlayer=   MediaPlayer.create(this, songId);
        pause.setBackgroundResource(R.drawable.pause);
        ispause=true;
        duration = mediaPlayer.getDuration();

        int currentPosition = mediaPlayer.getCurrentPosition();
        if(currentPosition== 0)  {
            proBar.setMax(duration);
            String maxTimeString = millisecondsToString(duration);
            timeEnd.setText(maxTimeString);
        }

        mediaPlayer.start();

        // Tạo một thread để update trạng thái của SeekBar.
        updateSeekBarThread= new UpdateSeekBarThread();
        threadHandler.postDelayed(updateSeekBarThread,50);
    }
}
