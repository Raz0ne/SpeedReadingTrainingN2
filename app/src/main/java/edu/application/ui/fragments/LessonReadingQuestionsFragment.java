package edu.application.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.application.databinding.FragmentLessonReadingQuestionsBinding;

public class LessonReadingQuestionsFragment extends Fragment {

    private FragmentLessonReadingQuestionsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLessonReadingQuestionsBinding
                .inflate(inflater, container, false);
        assert getArguments() != null;
        binding.questionTv.setText(getArguments().getString("question" +
                getArguments().getInt("question_index")));

        Bundle bundle = new Bundle();
        bundle.putInt("questions_count", getArguments().getInt("questions_count"));
        for (int i = 0; i < getArguments().getInt("questions_count"); i++) {

        }

        return binding.getRoot();
    }
}