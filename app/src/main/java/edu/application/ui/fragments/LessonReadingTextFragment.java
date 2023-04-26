package edu.application.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

        return binding.getRoot();
    }
}