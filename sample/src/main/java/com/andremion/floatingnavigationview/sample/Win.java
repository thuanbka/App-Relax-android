package com.andremion.floatingnavigationview.sample;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class Win extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_interface);
        TextView txt =(TextView)findViewById(R.id.monney);
        Button btn =(Button)findViewById(R.id.home);
        ArrayList<String>tien = new ArrayList<>();
        tien.add("0 VND");
        tien.add("200.000 VND");
        tien.add("600.000 VND");
        tien.add("1.200.000 VND");
        tien.add("2.000.000 VND");
        tien.add("3.000.000 VND");
        tien.add("4.200.000 VND");
        tien.add("5.600.000 VND");
        tien.add("7.200.000 VND");
        tien.add("9.000.000 VND");
        tien.add("11.000.000 VND");
        tien.add("13.200.000 VND");
        tien.add("15.600.000 VND");
        tien.add("18.200.000 VND");
        tien.add("21.000.000 VND");
        tien.add("24.000.000 VND");
        Intent intent =getIntent();
        int kt=0;
        int vt = getIntent().getIntExtra("vt",kt);
        txt.setText(tien.get(vt));
        if(vt==15||vt==2) {
            mediaPlayer = MediaPlayer.create(this, R.raw.bestplayer);
        }
        else
        {
            mediaPlayer= MediaPlayer.create(this, R.raw.lose);
        }
        mediaPlayer.start();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                Intent home =new Intent(Win.this,MainActivity.class);
                startActivity(home);
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
        }
    }
}
