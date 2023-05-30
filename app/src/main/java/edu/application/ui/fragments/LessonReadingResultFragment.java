package edu.application.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import edu.application.R;
import edu.application.databinding.FragmentLessonReadingResultBinding;

public class LessonReadingResultFragment extends Fragment {

    private FragmentLessonReadingResultBinding binding;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLessonReadingResultBinding
                .inflate(inflater, container, false);

        assert getArguments() != null;
        binding.scoreTv.append(" " + getArguments().getInt("correct_answers") + '/' +
                getArguments().getInt("questions_cnt"));

        binding.menuBtn.setOnClickListener(v ->
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_lessonReadingResultFragment_to_trainingFragment));

        return binding.getRoot();
    }
}
