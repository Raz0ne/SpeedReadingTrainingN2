package edu.application.ui.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import edu.application.R;
import edu.application.databinding.FragmentLessonReadingTextBinding;

public class LessonReadingTextFragment extends Fragment {

    private FragmentLessonReadingTextBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLessonReadingTextBinding.inflate(inflater, container, false);
        assert getArguments() != null;
        binding.text.setText(getArguments().getString("text"));

        SharedPreferences sharedPreferences = requireActivity().getPreferences(MODE_PRIVATE);
        binding.text.setBackgroundColor(sharedPreferences.getInt("lesson_background_color",
                ContextCompat.getColor(requireContext(), R.color.yellow_bg)));
        binding.text.setTextColor(sharedPreferences.getInt("lesson_text_color",
                ContextCompat.getColor(requireContext(), R.color.brown)));

        return binding.getRoot();
    }
}