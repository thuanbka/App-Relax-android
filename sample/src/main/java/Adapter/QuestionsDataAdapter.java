package Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andremion.floatingnavigationview.sample.R;

import java.util.List;
import Entity.Question;

public class QuestionsDataAdapter extends RecyclerView.Adapter<QuestionsDataAdapter.PlayerViewHolder> {
    public List<Question> questions;

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        private TextView question, answer, level;

        public PlayerViewHolder(View view) {
            super(view);
            question = (TextView) view.findViewById(R.id.question);
            answer = (TextView) view.findViewById(R.id.answer);
            level = (TextView) view.findViewById(R.id.level);
        }
    }

    public QuestionsDataAdapter(List<Question> players) {
        this.questions = players;
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_row_item, parent, false);

        return new PlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.question.setText(question.getQuestion());
        holder.answer.setText(question.getAnswerCorrect());
        switch (question.getLevel()%3)
        {
            case 0: holder.level.setText("Khó");break;
            case 1: holder.level.setText("Dễ");break;
            case 2: holder.level.setText("Trung Bình");break;
        }

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public void add(Question question)
    {
        questions.add(question);
    }
}
