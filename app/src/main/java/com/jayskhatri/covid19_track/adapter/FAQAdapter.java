package com.jayskhatri.covid19_track.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jayskhatri.covid19_track.R;
import com.jayskhatri.covid19_track.object.FAQuestion;

import java.util.List;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.MyViewHolder> {

    private List<FAQuestion> faqs;

    @NonNull
    @Override
    public FAQAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_single_faq, parent, false);

        return new FAQAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FAQAdapter.MyViewHolder holder, int position) {
        String question = faqs.get(position).getQuestion();
        String answer = faqs.get(position).getAnswer();
        holder.question.setText(question);
        holder.answer.setText(answer);
    }
    @Override
    public int getItemCount() {
        return faqs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView question, answer;

        public MyViewHolder(View view) {
            super(view);
            question = view.findViewById(R.id.text_view_question);
            answer = view.findViewById(R.id.text_view_ans);
        }
    }
    public FAQAdapter(List<FAQuestion> faqs) {
        this.faqs = faqs;
    }
}
