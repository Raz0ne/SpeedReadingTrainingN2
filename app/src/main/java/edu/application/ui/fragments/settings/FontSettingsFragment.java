package edu.application.ui.fragments.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.application.databinding.FragmentFontSettingsBinding;

public class FontSettingsFragment extends Fragment {

    private FragmentFontSettingsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentFontSettingsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}
