package com.andremion.floatingnavigationview.sample;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import Adapter.CustomGridAdapte1;
import Adapter.GridAdapter2;
import Entity.QuestionWord;
import Service.ServiceMusic;
import Service.ServiceWord;


public class CatchWord extends AppCompatActivity {

    private GridView gridView1;
    private GridView gridView2;
    static final String AB = "ABCDEFGHIKLMNOPQRSTUVXY";
    Button diem,cauhoi,btn;
    ImageView imageView;
    static int dem=1,score=0;
    ArrayList<String> list1;
    ArrayList<String> list2;
    GridAdapter2 adapter2;
    CustomGridAdapte1 adapter1;
    ArrayList<String> temp;
    ArrayList<Integer> temp1;
    QuestionWord dapan;

    ServiceWord mDB = new ServiceWord();
    ServiceMusic nDB;
    int []b =new int [2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questioncatchword);
        btn=(Button)findViewById(R.id.back);
        nDB=new ServiceMusic(getApplicationContext());
        b=nDB.getWord();
        dem=b[0];
        score=b[1];
        dapan= mDB.questionWord(dem-1);
        imageView=(ImageView)findViewById(R.id.image) ;
        diem=(Button)findViewById(R.id.score);
        diem.setText(""+score);
        cauhoi=(Button)findViewById(R.id.numberquestion);
        cauhoi.setText(""+dem);
        //goiy=(Button)findViewById(R.id.help);
        Resources res = getResources();
        String mDrawableName = dapan.icon;
        int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
        Drawable drawable = res.getDrawable(resID );
        imageView.setImageDrawable(drawable );


        gridView1= (GridView)findViewById(R.id.result);
        gridView2=(GridView)findViewById(R.id.idea) ;
        list1= new ArrayList<>();
        list2 = new ArrayList<>();
        temp = new ArrayList<>();
        for(int i=0;i<dapan.RemoveAccent().toUpperCase().length();i++)
        {
            list1.add(" ");
            temp.add(dapan.RemoveAccent().toUpperCase().charAt(i)+"");
        }
        int random=0;
        // Thêm random kí tự vào danh sách kết quả
        if(dapan.result.length()>10)
        {
            random=new Random().nextInt(3);
        }
        else
            random=new Random().nextInt(3)+2;
        int random2 =0;
        for(int i=1;i<=random;i++)
        {
            random2=new Random().nextInt(23);
            temp.add(AB.charAt(random2)+" ");
        }

        // Tạo random vị trí các kí tự
        while(temp.size()>0)
        {
            random2=new Random().nextInt(temp.size());
            list2.add(temp.get(random2));
            temp.remove(random2);
        }
        adapter2=new GridAdapter2(this, list2);
        gridView2.setAdapter(adapter2);
        temp1 =new ArrayList<Integer>();
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                if(list2.get(position)!=" ")
                {
                    int vt =searchindexemtyfirst();
                    if(vt!=-1) {
                        list1.set(vt, list2.get(position));
                        Log.d("tag", vt + "---" + list1.get(vt));
                        temp1.add(position);
                        list2.set(position, " ");
                        adapter2.notifyDataSetChanged();
                        adapter1.notifyDataSetChanged();
                    }
                    if(searchindexemtyfirst()==-1)
                    {
                        if(isCorrect()) {
                            Toast.makeText(CatchWord.this, "Chính xác: "+ dapan.result,Toast.LENGTH_SHORT).show();
                            if(dem==mDB.listQuestion.size())
                            {
                                Toast.makeText(CatchWord.this, "Chiến thắng",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(CatchWord.this, MainCatchWord.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                dem++;
                                score+=100;
                                if (nDB.UpdateWord(dem,score) != -1)
                                    Log.d("tag","Success");
                                else Log.d("tag","Fail");

                                Intent intent = new Intent(CatchWord.this, CatchWord.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else
                        {
                            Toast.makeText(CatchWord.this, "Không chính xác",Toast.LENGTH_SHORT).show();
                            for(int i=0;i<dapan.RemoveAccent().toUpperCase().length();i++)
                            {
                                list2.set(temp1.get(0),list1.get(i));
                                list1.set(i," ");
                                temp1.remove(0);
                            }
                            adapter2.notifyDataSetChanged();
                            adapter1.notifyDataSetChanged();
                        }

                    }

                }

            }
        });
        adapter1 = new CustomGridAdapte1(this, R.layout.itemcatchword1, list1);
        gridView1.setAdapter(adapter1);

        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                if(!list1.get(position).equals(" ")) {
                    list2.set(temp1.get(position), list1.get(position));
                    list1.set(position, " ");
                    temp1.remove(position);
                    adapter2.notifyDataSetChanged();
                    adapter1.notifyDataSetChanged();
                }

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatchWord.this, MainCatchWord.class);
                startActivity(intent);
                finish();
            }
        });

        Log.d("MainActivityLifecycle", "===== onCreat =====");
    }

    private boolean isCorrect() {

        String pattern1 ="";
        String pattern="";
        for(int i=0;i<list1.size();i++)
        {
            pattern1=pattern1+list1.get(i);
        }
        for(int i=0;i<pattern1.length();i++)
        {
            if(pattern1.charAt(i)!=' ')
            {
                pattern+=pattern1.charAt(i);
            }
        }
        if (pattern.equals(dapan.RemoveAccent().toUpperCase()))
            return  true;
        else
            return false;
    }
    public void ClickHelp( View view)
    {
        int vt =searchindexemtyfirst();
        if(vt !=-1) {
            int position = list2.indexOf(dapan.RemoveAccent().toUpperCase().charAt(vt) + "");
            list1.set(vt, list2.get(position));
            temp1.add(position);
            list2.set(position, " ");
            adapter2.notifyDataSetChanged();
            adapter1.notifyDataSetChanged();
        }
        if (searchindexemtyfirst()==-1)
        {
            if(isCorrect()) {
                Toast.makeText(CatchWord.this, "Chính xác: "+ dapan.result,Toast.LENGTH_SHORT).show();
                if(dem==mDB.listQuestion.size())
                {
                    nDB.UpdateWord(1,0);
                    Intent intent = new Intent(CatchWord.this, CatchWord.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    dem++;
                    score+=100;
                    nDB.UpdateWord(dem,score);
                    Intent intent = new Intent(CatchWord.this, CatchWord.class);
                    startActivity(intent);
                    finish();
                }
            }
            else
            {
                Toast.makeText(CatchWord.this, "Không chính xác",Toast.LENGTH_SHORT).show();
                for(int i=0;i<dapan.RemoveAccent().toUpperCase().length();i++)
                {
                    list2.set(temp1.get(0),list1.get(i));
                    list1.set(i," ");
                    temp1.remove(0);
                }
                adapter2.notifyDataSetChanged();
                adapter1.notifyDataSetChanged();
            }

        }
    }

    private int searchindexemtyfirst()
    {
        int index=-1;
        for(int i=0;i<list1.size();i++)
        {
            if(list1.get(i).equals(" "))
            {
                index=i;
                break;
            }
        }
        return  index;
    }




    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivityLifecycle", "===== onStart =====");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MainActivityLifecycle", "===== onRestart =====");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivityLifecycle", "===== onResume =====");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivityLifecycle", "===== onPause =====");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivityLifecycle", "===== onStop =====");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivityLifecycle", "===== onDestroy =====");
    }


}
