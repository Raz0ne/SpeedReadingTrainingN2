package edu.application.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;

import edu.application.R;
import edu.application.databinding.FragmentLessonReadingQuestionsBinding;

public class LessonReadingQuestionsFragment extends Fragment {

    private FragmentLessonReadingQuestionsBinding binding;
    private int questionIdx = 0;
    private int correctAnswers = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLessonReadingQuestionsBinding
                .inflate(inflater, container, false);

        assert getArguments() != null;

        ArrayList<String> answers = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                requireContext(), android.R.layout.simple_list_item_1, answers) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
                return textView;
            }
        };
        binding.answersList.setAdapter(adapter);

        binding.questionTv.setText(getArguments().getString("question" + 0));
        for (int j = 0; j < getArguments().getInt("answers_cnt" + 0); j++)
            answers.add(getArguments().getString("answer" + 0 + '_' + j));
        adapter.notifyDataSetChanged();


        binding.answersList.setOnItemClickListener((parent, itemClicked, position, id) -> {
            if (position + 1 == getArguments().getInt("correct_answer" + questionIdx))
                correctAnswers++;
            questionIdx++;
            if (questionIdx == getArguments().getInt("questions_count")) {
                Bundle bundle = new Bundle();
                bundle.putInt("correct_answers", correctAnswers);
                bundle.putInt("questions_cnt", getArguments().getInt("questions_count"));
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(
                        R.id.action_lessonReadingQuestionsFragment_to_lessonReadingResultFragment,
                        bundle);
            }
            else {
                answers.clear();
                binding.questionTv.setText(getArguments().getString("question" + questionIdx));
                for (int j = 0; j < getArguments().getInt("answers_cnt" + questionIdx); j++)
                    answers.add(getArguments().getString("answer" + questionIdx + '_' + j));
                adapter.notifyDataSetChanged();
            }
        });

        return binding.getRoot();
    }
}