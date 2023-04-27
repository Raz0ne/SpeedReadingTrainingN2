package edu.application.ui.fragments.settings;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.application.databinding.FragmentColorsSchemeCustomBinding;

public class ColorSchemeCustomFragment extends Fragment {

    private FragmentColorsSchemeCustomBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentColorsSchemeCustomBinding.inflate(inflater, container, false);

        binding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());

        binding.picker.addSVBar(binding.svBar);
        binding.picker.addOpacityBar(binding.opacityBar);

        binding.setBackgroundColorBtn.setOnClickListener(v -> {
            binding.picker.setOldCenterColor(binding.picker.getColor());
            binding.sampleTextTv.setBackgroundColor(binding.picker.getOldCenterColor());
        });
        binding.setTextColorBtn.setOnClickListener(v -> {
            binding.picker.setOldCenterColor(binding.picker.getColor());
            binding.sampleTextTv.setTextColor(binding.picker.getColor());
        });

        binding.saveBtn.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = requireActivity().getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("lesson_background_color",
                    ((ColorDrawable) binding.sampleTextTv.getBackground()).getColor());
            editor.putInt("lesson_text_color", binding.sampleTextTv.getCurrentTextColor());
            editor.apply();
        });


        return binding.getRoot();
    }
}
