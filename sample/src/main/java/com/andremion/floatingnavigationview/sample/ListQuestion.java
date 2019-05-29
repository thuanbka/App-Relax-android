package com.andremion.floatingnavigationview.sample;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import Adapter.QuestionsDataAdapter;
import Adapter.SwipeControllerActions;
import Adapter.SwipeControllerQuestion;
import Entity.Question;
import Service.ServiceMusic;

public class ListQuestion extends TabActivity {
    private QuestionsDataAdapter mAdapter;
    SwipeControllerQuestion swipeController = null;
    Button save,home;
    ServiceMusic mDB=null;
    EditText question=null;
    EditText answerA=null,answerB=null,answerC=null,answerD=null;
    RadioGroup level=null,answercorrect=null;
    int idUpdate;
    RecyclerView recyclerView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listquestion);
        mDB = new ServiceMusic(getApplicationContext());
        question=(EditText)findViewById(R.id.question) ;
        answerA=(EditText)findViewById(R.id.answerA) ;
        answerB=(EditText)findViewById(R.id.answerB) ;
        answerC=(EditText)findViewById(R.id.answerC) ;
        answerD=(EditText)findViewById(R.id.answerD) ;
        level=(RadioGroup)findViewById(R.id.level);
        answercorrect=(RadioGroup)findViewById(R.id.answer);
        setPlayersDataAdapter();
        setupRecyclerView();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(save.getText().toString().equals("Add"))
                {
                    addQuestion();
                }
                else if(save.getText().toString().equals("Update"))
                {
                    upDateQuestion();
                }
            }
        });
    }

    private void setPlayersDataAdapter() {
        List<Question> questions = new ArrayList<>();
        questions=mDB.getAllQuestion();
        mAdapter = new QuestionsDataAdapter(questions);
    }
    private void upDateQuestion()
    {
        Question question =new Question();
        question.setId(idUpdate);
        Log.d("ListQuestion1234"," id =" +idUpdate);
        question.setQuestion(this.question.getText().toString());
        question.setAnswerA(this.answerA.getText().toString());
        question.setAnswerB(this.answerB.getText().toString());
        question.setAnswerC(this.answerC.getText().toString());
        question.setAnswerD(this.answerD.getText().toString());
        switch (answercorrect.getCheckedRadioButtonId()) {
            case R.id.a:
                question.setDapan(0);
                break;
            case R.id.b:
                question.setDapan(1);
                break;
            case R.id.c:
                question.setDapan(2);
                break;
            case R.id.d:
                question.setDapan(3);
                break;
        }
        switch (level.getCheckedRadioButtonId()) {
            case R.id.easy:
                question.setLevel(1);
                break;
            case R.id.tb:
                question.setLevel(2);
                break;
            case R.id.kho:
                question.setLevel(3);
                break;
        }
        if(question.getQuestion().equals("")||question.getAnswerA().equals("")||question.getAnswerB().equals("")||question.getAnswerC().equals("")||question.getAnswerD().equals(""))
        {
            Toast.makeText(ListQuestion.this,"Thiếu dữ liệu",Toast.LENGTH_SHORT).show();
        }
        else
        {
            ServiceMusic mdb =new ServiceMusic(getApplicationContext());
           if(mdb.upDateQuestion(question)!=1)
           {
               Toast.makeText(ListQuestion.this,"Thất bại",Toast.LENGTH_SHORT).show();
           }
           else
           {
               Toast.makeText(ListQuestion.this,"Thành công",Toast.LENGTH_SHORT).show();
               setPlayersDataAdapter();
               recyclerView.setAdapter(mAdapter);

           }
           getTabHost().setCurrentTab(0);
        }
    }

    private  void addQuestion()
    {
        Question question =new Question();
        question.setQuestion(this.question.getText().toString());
        question.setAnswerA(this.answerA.getText().toString());
        question.setAnswerB(this.answerB.getText().toString());
        question.setAnswerC(this.answerC.getText().toString());
        question.setAnswerD(this.answerD.getText().toString());
        switch (answercorrect.getCheckedRadioButtonId()) {
            case R.id.a:
                question.setDapan(0);
                break;
            case R.id.b:
                question.setDapan(1);
                break;
            case R.id.c:
                question.setDapan(2);
                break;
            case R.id.d:
                question.setDapan(3);
                break;
        }
        switch (level.getCheckedRadioButtonId()) {
            case R.id.easy:
                question.setLevel(1);
                break;
            case R.id.tb:
                question.setLevel(2);
                break;
            case R.id.kho:
                question.setLevel(3);
                break;
        }
        if(question.getQuestion().equals("")||question.getAnswerA().equals("")||question.getAnswerB().equals("")||question.getAnswerC().equals("")||question.getAnswerD().equals(""))
        {
            Toast.makeText(ListQuestion.this,"Thiếu dữ liệu",Toast.LENGTH_SHORT).show();
        }
        else
        {
            ServiceMusic mdb =new ServiceMusic(getApplicationContext());
            mdb.addQuestion(question);
            setPlayersDataAdapter();
            recyclerView.setAdapter(mAdapter);
            getTabHost().setCurrentTab(0);
        }
    }


    private void setupRecyclerView() {
        recyclerView= (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
        home=(Button)findViewById(R.id.back) ;
        save=(Button)findViewById(R.id.save);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ListQuestion.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TabHost.TabSpec spec=getTabHost().newTabSpec("tag1");
        spec.setContent(R.id.questions);
        spec.setIndicator("List");
        getTabHost().addTab(spec);
        spec=getTabHost().newTabSpec("tag2");
        spec.setContent(R.id.details);
        spec.setIndicator("Details");
        getTabHost().addTab(spec);
        getTabHost().setCurrentTab(0);
        getTabHost().setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId.equals("tag1"))
                {
                    save.setText("Add");
                    question.setText("");
                    answerA.setText("");
                    answerB.setText("");
                    answerC.setText("");
                    answerD.setText("");
                    level.check(R.id.easy);
                    answercorrect.check(R.id.a);
                }
            }
        });

        swipeController = new SwipeControllerQuestion(getApplication(),new SwipeControllerActions() {

            @Override
            public void onRightClicked(int position) {
                Question current=mAdapter.questions.get(position);
                mDB.deleteQuestion(current);
                mAdapter.questions.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
            }

            @Override
            public void onLeftClicked(int position) {
                Question current=mAdapter.questions.get(position);
                idUpdate=current.getId();
                Log.d("ListQuestion1234"," id =" +idUpdate);
                question.setText(current.getQuestion());
                answerA.setText(current.getAnswerA());
                answerB.setText(current.getAnswerB());
                answerC.setText(current.getAnswerC());
                answerD.setText(current.getAnswerD());
                switch (current.getLevel()%3)
                {
                    case 0: level.check(R.id.kho);;break;
                    case 1: level.check(R.id.easy);;break;
                    case 2: level.check(R.id.tb);;break;
                }
                switch (current.getDapan()%4)
                {
                    case 0: answercorrect.check(R.id.a);;break;
                    case 1: answercorrect.check(R.id.b);;break;
                    case 2: answercorrect.check(R.id.c);;break;
                    case 3: answercorrect.check(R.id.d);;break;
                }
                getTabHost().setCurrentTab(1);
                save.setText("Update");
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }
}
