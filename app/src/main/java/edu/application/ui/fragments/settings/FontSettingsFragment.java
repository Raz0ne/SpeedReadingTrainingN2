package edu.application.ui.fragments.settings;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.application.R;
import edu.application.databinding.FragmentFontSettingsBinding;
import edu.application.ui.adapters.TextFormatter;

public class FontSettingsFragment extends Fragment {

    private FragmentFontSettingsBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentFontSettingsBinding.inflate(inflater, container, false);

        sharedPreferences = requireActivity().getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();

        binding.fontSizeTv.setText(getText(R.string.font_settings_font_size_tv_txt) + " " +
                sharedPreferences.getInt("lesson_text_size", 16));
        binding.fontSizeSlider.setValue(sharedPreferences.getInt("lesson_text_size", 16));

        binding.lineSpacingTv.setText(getText(R.string.font_settings_line_spacing_tv_txt) + " " +
                sharedPreferences.getFloat("lesson_text_line_spacing", 1));
        binding.lineSpacingSlider.setValue(
                sharedPreferences.getFloat("lesson_text_line_spacing", 1));

        binding.textAlignmentSwitch.setChecked(
                sharedPreferences.getBoolean("lesson_text_justification", false));

        TextFormatter.setBorder(binding.sampleTextCv);
        TextFormatter.setTextSettings(binding.cvText);

        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fontSizeSlider.addOnChangeListener((slider, value, fromUser) -> {
            if (fromUser) {
                binding.fontSizeTv.setText(getText(R.string.font_settings_font_size_tv_txt) + " " + (int) value);
                binding.cvText.setTextSize((int) value);
            }
        });

        binding.lineSpacingSlider.addOnChangeListener((slider, value, fromUser) -> {
            if (fromUser) {
                binding.lineSpacingTv.setText(getText(R.string.font_settings_line_spacing_tv_txt) +
                        " " + value);
                binding.cvText.setLineSpacing(0, value);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            binding.textAlignmentSwitch.setOnCheckedChangeListener((v, isChecked) ->
                    binding.cvText.setJustificationMode(
                            TextFormatter.booleanToJustificationMode(isChecked)));

        binding.saveBtn.setOnClickListener(v -> {
            editor.putInt("lesson_text_size", (int) binding.fontSizeSlider.getValue());
            editor.putFloat("lesson_text_line_spacing", binding.lineSpacingSlider.getValue());
            editor.putBoolean("lesson_text_justification",
                    binding.textAlignmentSwitch.isChecked());
            editor.apply();
        });
    }
}
