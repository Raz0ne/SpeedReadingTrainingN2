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
import edu.application.ui.adapters.TextFormatter;

public class ColorSchemeCustomFragment extends Fragment {

    private FragmentColorsSchemeCustomBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentColorsSchemeCustomBinding.inflate(inflater, container, false);

        sharedPreferences = requireActivity().getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();

        binding.picker.addSVBar(binding.svBar);
        binding.picker.addOpacityBar(binding.opacityBar);

        TextFormatter.setColor(binding.cvText);
        TextFormatter.setBorder(binding.sampleTextCv);
        TextFormatter.setTextSize(binding.cvText);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());

        binding.setBackgroundColorBtn.setOnClickListener(v -> {
            binding.picker.setOldCenterColor(binding.picker.getColor());
            binding.cvText.setBackgroundColor(binding.picker.getOldCenterColor());
        });
        binding.setTextColorBtn.setOnClickListener(v -> {
            binding.picker.setOldCenterColor(binding.picker.getColor());
            binding.cvText.setTextColor(binding.picker.getColor());
            binding.sampleTextCv.setStrokeColor(binding.picker.getColor());
        });

        binding.saveBtn.setOnClickListener(v -> {
            editor.putInt("lesson_background_color",
                    ((ColorDrawable) binding.cvText.getBackground()).getColor());
            editor.putInt("lesson_text_color", binding.cvText.getCurrentTextColor());
            editor.apply();
        });
    }
}
