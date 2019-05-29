package com.andremion.floatingnavigationview.sample;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

import Entity.Question;
import Service.ServiceMusic;

public class PlayMilion extends AppCompatActivity implements View.OnClickListener {
    private TextView tvQuestion;
    private Button btnCaseA;
    private Button btnCaseB;
    private Button btnCaseC;
    private Button btnCaseD;
    private ServiceMusic mDB;
    private TextView tvTimedown;
    private TextView tvSoCau;
    public String textCall="";
    private int trueCase;
    private int i;
    private int dem;
    private int wait;
    private boolean check;
    private TextView tvCoin;
    private boolean run;
    private int coin;
    private Dialog dialog;
    private ArrayList<Question> questions = new ArrayList<>();
    private ImageView imHelp_5050;
    private ImageView imHelp_audience;
    private ImageView imHelp_call;
    private ImageView imHelp_stop;
    private MediaPlayer mediaPlayer;
    private ImageView imClock;
    private int timeRunHelp;
    private int idHelp;
    private Animation animationButton;
    private boolean checkPickAnswer;
    private MediaPlayer mda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ailatrieuphu_play);
        mDB = new ServiceMusic(getApplicationContext());
        questions = mDB.getData();
        initView();
        asyncTask.execute();
        Log.d("1234567abc","oncreate");
    }

    private void initView() {
        i = 0;
        mediaPlayer= MediaPlayer.create(this,R.raw.ques01);
        mediaPlayer.start();
        dem = 30;
        wait = 30;
        coin = 0;
        timeRunHelp = 30;
        imClock = (ImageView) findViewById(R.id.imClock);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.time_round);
        imClock.startAnimation(animation);
        tvQuestion = (TextView) findViewById(R.id.txt_questions);
        tvTimedown = (TextView) findViewById(R.id.tv_timeDown);
        tvCoin = (TextView) findViewById(R.id.tvCoin);
        tvSoCau = (TextView) findViewById(R.id.tvSoCau);
        btnCaseA = (Button) findViewById(R.id.answer_A);
        btnCaseB = (Button) findViewById(R.id.answer_B);
        btnCaseC = (Button) findViewById(R.id.answer_C);
        btnCaseD = (Button) findViewById(R.id.answer_D);
        imHelp_5050 = (ImageView) findViewById(R.id.help_5050);
        imHelp_audience = (ImageView) findViewById(R.id.help_audience);
        imHelp_call = (ImageView) findViewById(R.id.help_call);
        imHelp_stop = (ImageView) findViewById(R.id.help_stop);
        mda= MediaPlayer.create(this,R.raw.moc1);
        mda.start();
        setText(i);
        btnCaseA.setOnClickListener(this);
        btnCaseB.setOnClickListener(this);
        btnCaseC.setOnClickListener(this);
        btnCaseD.setOnClickListener(this);
        imHelp_5050.setOnClickListener(this);
        imHelp_audience.setOnClickListener(this);
        imHelp_call.setOnClickListener(this);
        imHelp_stop.setOnClickListener(this);
    }
    public static final int[] SOUND_QUESTIONS={
            R.raw.ques01,
            R.raw.ques02,
            R.raw.ques03,
            R.raw.ques04,
            R.raw.ques05,
            R.raw.ques06,
            R.raw.ques07,
            R.raw.ques08,
            R.raw.ques09,
            R.raw.ques10,
            R.raw.ques11,
            R.raw.ques12,
            R.raw.ques13,
            R.raw.ques14,
            R.raw.ques15,
    };

    private void setText(int i) {
        run=true;
        int tm=i+1;
        tvCoin.setText(coin+"");
        tvSoCau.setText("Câu hỏi số "+tm+" :");
        playSound(SOUND_QUESTIONS[i]);
        if(i>9)
        {
            if(mda.isPlaying()) {
                mda.stop();
            }
            mda= MediaPlayer.create(this,R.raw.moc3);
            mda.start();
        }
        else if(i>4)
        {
            if(mda.isPlaying()) {
                mda.stop();
            }
            mda= MediaPlayer.create(this,R.raw.moc2);
            mda.start();
        }
        else
        {
            if(mda.isPlaying()) {
                mda.stop();
            }
            mda= MediaPlayer.create(this,R.raw.moc1);
            mda.start();
        }
        checkPickAnswer=true;
        btnCaseA.setBackgroundResource(R.drawable.button3);
        btnCaseB.setBackgroundResource(R.drawable.button3);
        btnCaseC.setBackgroundResource(R.drawable.button3);
        btnCaseD.setBackgroundResource(R.drawable.button3);
        tvQuestion.setText(questions.get(i).getQuestion());
        btnCaseA.setText("   A. "+questions.get(i).getAnswerA());
        btnCaseB.setText("   B. "+questions.get(i).getAnswerB());
        btnCaseC.setText("   C. "+questions.get(i).getAnswerC());
        btnCaseD.setText("   D. "+questions.get(i).getAnswerD());
        btnCaseA.setEnabled(true);
        btnCaseB.setEnabled(true);
        btnCaseC.setEnabled(true);
        btnCaseD.setEnabled(true);
        dem=30;
        switch (questions.get(i).getDapan()){
            case 0:
                trueCase=R.id.answer_A;
                break;
            case 1:
                trueCase=R.id.answer_B;
                break;
            case 2:
                trueCase=R.id.answer_C;
                break;
            case 3:
                trueCase=R.id.answer_D;
                break;
        }
    }

    private AsyncTask<Void,Integer,Void> asyncTask =new AsyncTask<Void, Integer, Void>() {
        @Override
        protected Void doInBackground(Void... voids) {
            for (dem=30;dem>=0;dem--){
                if(isCancelled())
                {
                    break;
                }
                else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    publishProgress(dem);
                }
            }
            asyncTask.cancel(true);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (run) {
                tvTimedown.setText(values[0] + "");
            }
            else{
                dem++;
            }
            wait++;
            timeRunHelp++;
            showHelp(timeRunHelp,idHelp);
            if (wait==4 && check){
                switch (trueCase){
                    case R.id.answer_A:
                        btnCaseA.setBackgroundResource(R.drawable.answer_true);
                        playSound(R.raw.true_a);
                        animnButon(btnCaseA);
                        break;
                    case R.id.answer_B:
                        btnCaseB.setBackgroundResource(R.drawable.answer_true);
                        playSound(R.raw.true_b);
                        animnButon(btnCaseB);
                        break;
                    case R.id.answer_C:
                        btnCaseC.setBackgroundResource(R.drawable.answer_true);
                        playSound(R.raw.true_c);
                        animnButon(btnCaseC);
                        break;
                    case R.id.answer_D:
                        btnCaseD.setBackgroundResource(R.drawable.answer_true);
                        playSound(R.raw.true_d);
                        animnButon(btnCaseD);
                        break;
                }
            }
            if (wait==4 && !check){
                switch (trueCase){
                    case R.id.answer_A:
                        btnCaseA.setBackgroundResource(R.drawable.answer_false);
                        playSound(R.raw.lose_a);
                        animnButon(btnCaseA);
                        break;
                    case R.id.answer_B:
                        btnCaseB.setBackgroundResource(R.drawable.answer_false);
                        animnButon(btnCaseB);
                        break;
                    case R.id.answer_C:
                        btnCaseC.setBackgroundResource(R.drawable.answer_false);
                        playSound(R.raw.lose_c);
                        animnButon(btnCaseC);
                        break;
                    case R.id.answer_D:
                        btnCaseD.setBackgroundResource(R.drawable.answer_false);
                        playSound(R.raw.lose_d);
                        animnButon(btnCaseD);
                        break;
                }
            }
            if ((wait==6 && !check) || dem==0){
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                asyncTask.cancel(true);
                Intent intent= new Intent(PlayMilion.this,Win.class);
                intent.putExtra("vt",i);
                startActivity(intent);
                finish();
            }
            if (wait==6 && check) {
                i++;
                if(i==15)
                {
                    if(mediaPlayer.isPlaying())
                    {
                        mediaPlayer.stop();
                    }
                    asyncTask.cancel(true);
                    Intent intent= new Intent(PlayMilion.this,Win.class);
                    intent.putExtra("vt",i);
                    startActivity(intent);
                    finish();
                }
                else {
                    setText(i);
                }
            }

        }
    };

    public void playSound(int type){
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
        }
        mediaPlayer= MediaPlayer.create(this,type);
        mediaPlayer.start();
    }

    public void showHelp(int timeRunHelp,int idHelp){
        if (timeRunHelp==4 && idHelp== R.id.help_5050){
            ArrayList<Integer> array =new ArrayList<Integer>();
            array.add(1);array.add(2);array.add(3);array.add(4);
            if (trueCase==R.id.answer_A){
                array.remove(0);
            }else if (trueCase==R.id.answer_B){
                array.remove(1);
            }else if (trueCase==R.id.answer_C){
                array.remove(2);
            }else if (trueCase==R.id.answer_D){
                array.remove(3);
            }
            for(int i=0;i<2;i++)
            {
                int random =new Random().nextInt(array.size());
                if(array.get(random)==1)
                {
                    btnCaseA.setEnabled(false);
                    btnCaseA.setText("");
                }
                else if(array.get(random)==2)
                {
                    btnCaseB.setEnabled(false);
                    btnCaseB.setText("");
                }
                else if(array.get(random)==3)
                {
                    btnCaseC.setEnabled(false);
                    btnCaseC.setText("");
                }
                else if(array.get(random)==4)
                {
                    btnCaseD.setEnabled(false);
                    btnCaseD.setText("");
                }
                array.remove(random);
            }
            playSound(R.raw.s50);
            run=true;
            checkPickAnswer=true;
        }
        else if (idHelp== R.id.help_audience){
            if (timeRunHelp==6){
                playSound(R.raw.bg_audience);
            }
            if (timeRunHelp==11){
                checkPickAnswer=true;
                 initDialogAudience();
            }
        }else if (timeRunHelp==3 && idHelp==R.id.help_call){
            checkPickAnswer=true;
              initDiaLogHelpCall();
        }

    }

    public void initDiaLogHelpCall(){
        dialog=new Dialog(this,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        LayoutInflater layoutInflater= LayoutInflater.from(this);
        View view= layoutInflater.inflate(R.layout.dialog_call,null);
        ImageView imObama= (ImageView) view.findViewById(R.id.help_obama);
        ImageView imStever= (ImageView) view.findViewById(R.id.help_stever);
        ImageView imBillGate= (ImageView) view.findViewById(R.id.help_billgate);
        ImageView imTroll= (ImageView) view.findViewById(R.id.help_troll);
        imObama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 textCall="O-ba-ma khuyên bạn chọn:";
                showDiaLogAnswerCall();
            }
        });
        imStever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCall="CEO Apple nghĩ đáp án đúng là:";
                showDiaLogAnswerCall();
            }
        });
        imTroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCall="Với 20 năm đá sân nhỏ cỏ đen thì tôi nghĩ đáp án là:";
                showDiaLogAnswerCall();
            }
        });
        imBillGate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCall="Với số tiền mà tôi có thì dáp án đúng là:";
                showDiaLogAnswerCall();
            }
        });
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();
    }
    public void showDiaLogAnswerCall(){
        dialog.dismiss();
        dialog=new Dialog(this,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        LayoutInflater layoutInflater= LayoutInflater.from(this);
        View view= layoutInflater.inflate(R.layout.show_answer_call,null);
        TextView txt =(TextView)view.findViewById(R.id.text_inion);
        txt.setText(textCall);
        final TextView tvShowAnswer= (TextView) view.findViewById(R.id.show_answer);
        int random=new Random().nextInt(7);
        if( random>3) {
            if (trueCase == R.id.answer_A) {
                tvShowAnswer.setText("A");
            } else if (trueCase == R.id.answer_B) {
                tvShowAnswer.setText("B");
            } else if (trueCase == R.id.answer_C) {
                tvShowAnswer.setText("C");
            } else if (trueCase == R.id.answer_D) {
                tvShowAnswer.setText("D");
            }
        }
        else
        {
            if (random == 3) {
                tvShowAnswer.setText("A");
            } else if (random == 2) {
                tvShowAnswer.setText("B");
            } else if (random == 1) {
                tvShowAnswer.setText("C");
            } else if (random == 0) {
                tvShowAnswer.setText("D");
            }
        }
        Button btnOk= (Button) view.findViewById(R.id.btn_ok_help_call);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                run=true;
            }
        });
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();
    }
    @Override
    public void onClick(View v) {
        run=false;
        if (!checkPickAnswer)
        {
            return;
        }
        if (v.getId()==R.id.answer_A ||v.getId()==R.id.answer_B||v.getId()==R.id.answer_C||v.getId()==R.id.answer_D)
        {
            v.setBackgroundResource(R.drawable.answer_choose);
            switch (v.getId()){
                case R.id.answer_A:
                    playSound(R.raw.ans_a);
                    break;
                case R.id.answer_B:
                    playSound(R.raw.ans_b);
                    break;
                case R.id.answer_C:
                    playSound(R.raw.ans_c);
                    break;
                case R.id.answer_D:
                    playSound(R.raw.ans_d);
                    break;
            }
            if (v.getId()==trueCase){
                check=true;
                wait=0;
                coin= coin+200*(i+1);
            }else {
                check=false;
                wait=0;
            }
            checkPickAnswer=false;
        }
        else if (v.getId()==R.id.help_5050){
            checkPickAnswer=false;
            timeRunHelp=0;
            idHelp=R.id.help_5050;
            playSound(R.raw.sound5050);
            imHelp_5050.setEnabled(false);
            imHelp_5050.setImageResource(R.drawable.atp__activity_player_button_image_help_5050_x);
        }else if (v.getId()==R.id.help_audience){
            checkPickAnswer=false;
            timeRunHelp=0;
            idHelp=R.id.help_audience;
            imHelp_audience.setEnabled(false);
            playSound(R.raw.khan_gia);
            imHelp_audience.setImageResource(R.drawable.atp__activity_player_button_image_help_audience_x);
        }else if (v.getId()==R.id.help_call){
            checkPickAnswer=false;
            timeRunHelp=0;
            idHelp=R.id.help_call;
            imHelp_call.setEnabled(false);
            playSound(R.raw.call);
            imHelp_call.setImageResource(R.drawable.atp__activity_player_button_image_help_call_x);
        }else if (v.getId()==R.id.help_stop){
            checkPickAnswer=false;
            asyncTask.cancel(true);
            if(mediaPlayer.isPlaying())
            {
                mediaPlayer.stop();
            }
            Intent intent= new Intent(PlayMilion.this,Win.class);
            intent.putExtra("vt", i);
            startActivity(intent);
            finish();
        }
    }

    public void initDialogAudience(){
        dialog=new Dialog(this,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        LayoutInflater layoutInflater= LayoutInflater.from(this);
        View view= layoutInflater.inflate(R.layout.dialog_audience,null);
        TextView tvShowAnswerA= (TextView) view.findViewById(R.id.audience_A);
        TextView tvShowAnswerB= (TextView) view.findViewById(R.id.audience_B);
        TextView tvShowAnswerC= (TextView) view.findViewById(R.id.audience_C);
        TextView tvShowAnswerD= (TextView) view.findViewById(R.id.audience_D);
        Button btnOk= (Button) view.findViewById(R.id.btn_ok_help_audence);
        Random random= new Random();
        int rd1True= random.nextInt(70)+30;
        int rdFalse1=random.nextInt(100-rd1True);
        int rdFalse2=random.nextInt(100-rd1True-rdFalse1);
        int rdFalse3=100-rd1True-rdFalse1-rdFalse2;
        if(btnCaseA.getText().equals("")||btnCaseB.getText().equals("")||btnCaseC.getText().equals("")||btnCaseD.getText().equals(""))
        {
            rdFalse1=100-rd1True;
            rdFalse2=0;
            rdFalse3=0;
        }
        if (trueCase==R.id.answer_A){
            tvShowAnswerA.setText(rd1True+"%");
            if(btnCaseB.getText().equals("")||btnCaseC.getText().equals("")||btnCaseD.getText().equals(""))
            {
                if(!btnCaseB.getText().equals(""))
                {
                    tvShowAnswerB.setText(rdFalse1 + "%");
                    tvShowAnswerC.setText(rdFalse2 + "%");
                    tvShowAnswerD.setText(rdFalse3 + "%");
                }
                else if(!btnCaseC.getText().equals(""))
                {
                    tvShowAnswerC.setText(rdFalse1 + "%");
                    tvShowAnswerB.setText(rdFalse2 + "%");
                    tvShowAnswerD.setText(rdFalse3 + "%");
                }
                else if(!btnCaseD.getText().equals(""))
                {
                    tvShowAnswerD.setText(rdFalse1 + "%");
                    tvShowAnswerC.setText(rdFalse2 + "%");
                    tvShowAnswerB.setText(rdFalse3 + "%");
                }
            }
            else {
                tvShowAnswerB.setText(rdFalse1 + "%");
                tvShowAnswerC.setText(rdFalse2 + "%");
                tvShowAnswerD.setText(rdFalse3 + "%");
            }
        }else if (trueCase==R.id.answer_B){
            tvShowAnswerB.setText(rd1True + "%");
            if(btnCaseA.getText().equals("")||btnCaseC.getText().equals("")||btnCaseD.getText().equals(""))
            {
                if(!btnCaseA.getText().equals(""))
                {
                    tvShowAnswerA.setText(rdFalse1 + "%");
                    tvShowAnswerC.setText(rdFalse2 + "%");
                    tvShowAnswerD.setText(rdFalse3 + "%");
                }
                else if(!btnCaseC.getText().equals(""))
                {
                    tvShowAnswerC.setText(rdFalse1 + "%");
                    tvShowAnswerA.setText(rdFalse2 + "%");
                    tvShowAnswerD.setText(rdFalse3 + "%");
                }
                else if(!btnCaseD.getText().equals(""))
                {
                    tvShowAnswerD.setText(rdFalse1 + "%");
                    tvShowAnswerC.setText(rdFalse2 + "%");
                    tvShowAnswerA.setText(rdFalse3 + "%");
                }
            }
            else {
                tvShowAnswerA.setText(rdFalse1 + "%");
                tvShowAnswerC.setText(rdFalse2 + "%");
                tvShowAnswerD.setText(rdFalse3 + "%");
            }
        }else if (trueCase==R.id.answer_C){
            tvShowAnswerC.setText(rd1True+"%");
            if(btnCaseA.getText().equals("")||btnCaseB.getText().equals("")||btnCaseD.getText().equals(""))
            {
                if(!btnCaseA.getText().equals(""))
                {
                    tvShowAnswerA.setText(rdFalse1 + "%");
                    tvShowAnswerB.setText(rdFalse2 + "%");
                    tvShowAnswerD.setText(rdFalse3 + "%");
                }
                else if(!btnCaseB.getText().equals(""))
                {
                    tvShowAnswerB.setText(rdFalse1 + "%");
                    tvShowAnswerA.setText(rdFalse2 + "%");
                    tvShowAnswerD.setText(rdFalse3 + "%");
                }
                else if(!btnCaseD.getText().equals(""))
                {
                    tvShowAnswerD.setText(rdFalse1 + "%");
                    tvShowAnswerB.setText(rdFalse2 + "%");
                    tvShowAnswerA.setText(rdFalse3 + "%");
                }
            }
            else
            {
                tvShowAnswerA.setText(rdFalse1 + "%");
                tvShowAnswerB.setText(rdFalse2 + "%");
                tvShowAnswerD.setText(rdFalse3 + "%");
            }
        }else if (trueCase==R.id.answer_D){
            tvShowAnswerD.setText(rd1True+"%");
            if(btnCaseA.getText().equals("")||btnCaseC.getText().equals("")||btnCaseB.getText().equals(""))
            {
                if(!btnCaseA.getText().equals(""))
                {
                    tvShowAnswerA.setText(rdFalse1 + "%");
                    tvShowAnswerC.setText(rdFalse2 + "%");
                    tvShowAnswerB.setText(rdFalse3 + "%");
                }
                else if(!btnCaseC.getText().equals(""))
                {
                    tvShowAnswerC.setText(rdFalse1 + "%");
                    tvShowAnswerA.setText(rdFalse2 + "%");
                    tvShowAnswerB.setText(rdFalse3 + "%");
                }
                else if(!btnCaseB.getText().equals(""))
                {
                    tvShowAnswerB.setText(rdFalse1 + "%");
                    tvShowAnswerC.setText(rdFalse2 + "%");
                    tvShowAnswerA.setText(rdFalse3 + "%");
                }
            }
            else
            {
                tvShowAnswerA.setText(rdFalse1 + "%");
                tvShowAnswerB.setText(rdFalse2 + "%");
                tvShowAnswerC.setText(rdFalse3 + "%");
            }

        }
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                run=true;
            }
        });
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();
    }


    public void animnButon(Button btn){
        animationButton= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_check);
        btn.startAnimation(animationButton);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("1234567abc","onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("1234567abc","onrestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("123456a","start");
        mda.start();
        mediaPlayer.start();
        Log.d("1234567abc","onstart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("1234567abc","onpause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("123456a","stop");
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
        }
        if(mda.isPlaying())
        {
            mda.stop();
        }
        Log.d("1234567abc","onstop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
        }
        if(mda.isPlaying())
        {
            mda.stop();
        }
        asyncTask.cancel(true);
        Log.d("1234567abc","ondestroy");
    }
}
