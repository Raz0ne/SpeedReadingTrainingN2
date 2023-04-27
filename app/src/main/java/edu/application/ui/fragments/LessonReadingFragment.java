package edu.application.ui.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import edu.application.R;
import edu.application.databinding.FragmentLessonReadingBinding;

public class LessonReadingFragment extends Fragment {

    private FragmentLessonReadingBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLessonReadingBinding.inflate(inflater, container, false);

        assert getArguments() != null;
        //Text reading
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_lessonReadingFragment_to_lessonReadingTextFragment,
                        getArguments());

        return binding.getRoot();
    }
}
