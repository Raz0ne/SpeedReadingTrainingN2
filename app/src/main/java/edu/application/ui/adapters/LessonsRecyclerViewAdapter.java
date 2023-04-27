package edu.application.ui.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.application.R;
import edu.application.data.models.Lesson;
import edu.application.data.models.LessonQuestion;
import edu.application.databinding.FragmentLessonItemViewBinding;

public class LessonsRecyclerViewAdapter extends
        RecyclerView.Adapter<LessonsRecyclerViewAdapter.RecyclerViewItemViewHolder> {
    List<Lesson> data;

    public LessonsRecyclerViewAdapter() {
        this.data = new ArrayList<>();
    }


    public LessonsRecyclerViewAdapter(List<Lesson> data) {
        this.data = data;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Lesson> newData) {
        data = newData;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentLessonItemViewBinding mBinding =
                FragmentLessonItemViewBinding.inflate(LayoutInflater.from(parent.getContext()));

        return new RecyclerViewItemViewHolder(mBinding.getRoot());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewItemViewHolder holder, int position) {
        Lesson lesson = data.get(position);
        holder.binding.lessonTextName.setText(lesson.getTextName());
        holder.binding.lessonTextAuthor.setText(lesson.getTextAuthor());
        holder.binding.lessonAgeCategory.setText(
                String.valueOf(lesson.getAgeMin()) + '-' + lesson.getAgeMax());
        holder.binding.lessonTextSize.setText(lesson.getSize());

        holder.binding.getRoot().setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("text", lesson.getText());
            bundle.putInt("questions_count", lesson.getQuestions().size());
            for (int i = 0; i < lesson.getQuestions().size(); i++) {
                LessonQuestion question = lesson.getQuestions().get(i);
                bundle.putString("question" + i, question.getQuestion());
                bundle.putString("answer" + i, question.getAnswer());
            }
            Navigation.findNavController(
                    (Activity) holder.itemView.getContext(), R.id.nav_host_fragment)
                    .navigate(R.id.action_trainingFragment_to_lessonReadingFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class RecyclerViewItemViewHolder extends RecyclerView.ViewHolder {
        public FragmentLessonItemViewBinding binding;

        public RecyclerViewItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setLayoutParams(new RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            binding = FragmentLessonItemViewBinding.bind(itemView);
        }
    }
}
