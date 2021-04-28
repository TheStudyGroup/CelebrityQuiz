package com.thestudygroup.celebrityquiz.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thestudygroup.celebrityquiz.R;
import com.thestudygroup.celebrityquiz.vo.QuestionVO;

import java.util.List;
import java.util.Objects;

public class SolutionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final List<QuestionVO> questions;

    public SolutionAdapter(@NonNull final List<QuestionVO> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int viewType) {
        final LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.item_solution, viewGroup, false);
        return new RecyclerView.ViewHolder(view) {};
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        if (questions.isEmpty()) {
            return;
        }

        final TextView    viewQuestion = viewHolder.itemView.findViewById(R.id.item_sol_text_content);
        final ImageView   imageView    = viewHolder.itemView.findViewById(R.id.item_sol_image);
        final RadioGroup  radioGroup   = viewHolder.itemView.findViewById(R.id.item_sol_radio_group);
        final RadioButton radioButton1 = viewHolder.itemView.findViewById(R.id.item_sol_radio1);
        final RadioButton radioButton2 = viewHolder.itemView.findViewById(R.id.item_sol_radio2);
        final RadioButton radioButton3 = viewHolder.itemView.findViewById(R.id.item_sol_radio3);
        final RadioButton radioButton4 = viewHolder.itemView.findViewById(R.id.item_sol_radio4);
        final EditText    textAnswer   = viewHolder.itemView.findViewById(R.id.item_sol_answer_right);
        final EditText    textUser     = viewHolder.itemView.findViewById(R.id.item_sol_answer_user);
        final QuestionVO quiz         = questions.get(position);

        viewQuestion.setText(String.format("%s. %s", position + 1, quiz.question));
        Glide.with(imageView.getContext()).load(quiz.imageUrl).into(imageView);

        if (quiz.correctAnswer != 0) {
            textAnswer.setVisibility(View.GONE);
            textUser.setVisibility(View.GONE);
            radioButton1.setText(quiz.one);
            radioButton2.setText(quiz.two);
            radioButton3.setText(quiz.three);
            radioButton4.setText(quiz.four);

            RadioButton r = null;
            if (quiz.correctAnswer == 1) r = radioButton1;
            if (quiz.correctAnswer == 2) r = radioButton2;
            if (quiz.correctAnswer == 3) r = radioButton3;
            if (quiz.correctAnswer == 4) r = radioButton4;
            if (r == null) return;
            r.setTextColor(Color.parseColor("#FF0BA512"));

            if (quiz.userAnswer == quiz.correctAnswer) {
                r.setChecked(true);
            } else {
                RadioButton u = null;
                if (quiz.userAnswer == 1) u = radioButton1;
                if (quiz.userAnswer == 2) u = radioButton2;
                if (quiz.userAnswer == 3) u = radioButton3;
                if (quiz.userAnswer == 4) u = radioButton4;
                if (u != null) {
                    u.setChecked(true);
                    u.setTextColor(Color.RED);
                }
            }
        } else {
            radioGroup.setVisibility(View.GONE);
            textAnswer.setTextColor(Color.parseColor("#FF0BA512"));
            textUser.setTextColor(Color.RED);
            textAnswer.setText(quiz.one);
            textUser.setText(quiz.two);
            if (Objects.equals(quiz.one.toLowerCase(), quiz.two.toLowerCase())) {
                textUser.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
