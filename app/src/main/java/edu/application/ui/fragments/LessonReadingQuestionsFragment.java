package edu.application.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;

import edu.application.R;
import edu.application.databinding.FragmentLessonReadingQuestionsBinding;

public class LessonReadingQuestionsFragment extends Fragment {

    private FragmentLessonReadingQuestionsBinding binding;
    private int questionIdx = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLessonReadingQuestionsBinding
                .inflate(inflater, container, false);

        assert getArguments() != null;

        ArrayList<String> answers = new ArrayList<>();

        binding.questionTv.setText(getArguments().getString("question" + 0));
        for (int j = 0; j < getArguments().getInt("answers_cnt" + 0); j++) {
            Log.d("razon", String.valueOf(j));
            answers.add(getArguments().getString("answer" + 0 + '_' + j));
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    requireContext(), android.R.layout.simple_list_item_1, answers);
            binding.answersList.setAdapter(adapter);
        }

        binding.answersList.setOnItemClickListener((parent, itemClicked, position, id) -> {
            questionIdx++;
            if (questionIdx == getArguments().getInt("questions_count"))
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(
                        R.id.action_lessonReadingQuestionsFragment_to_lessonReadingResultFragment,
                        getArguments());
            else {
                answers.clear();
                binding.questionTv.setText(getArguments().getString("question" + questionIdx));
                for (int j = 0; j < getArguments().getInt("answers_cnt" + questionIdx); j++) {
                    answers.add(getArguments().getString("answer" + questionIdx + '_' + j));
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            requireContext(), android.R.layout.simple_list_item_1, answers);
                    binding.answersList.setAdapter(adapter);
                }
            }
        });

        return binding.getRoot();
    }
}