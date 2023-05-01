package edu.application.ui.fragments.settings;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import edu.application.R;
import edu.application.databinding.FragmentColorsSchemeCustomBinding;

public class ColorSchemeCustomFragment extends Fragment {

    private FragmentColorsSchemeCustomBinding binding;

    private TextView cvText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentColorsSchemeCustomBinding.inflate(inflater, container, false);

        cvText = binding.sampleTextCv.findViewById(R.id.cv_text);

        binding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());

        binding.picker.addSVBar(binding.svBar);
        binding.picker.addOpacityBar(binding.opacityBar);

        binding.setBackgroundColorBtn.setOnClickListener(v -> {
            binding.picker.setOldCenterColor(binding.picker.getColor());
            cvText.setBackgroundColor(binding.picker.getOldCenterColor());
        });
        binding.setTextColorBtn.setOnClickListener(v -> {
            binding.picker.setOldCenterColor(binding.picker.getColor());
            cvText.setTextColor(binding.picker.getColor());
            binding.sampleTextCv.setStrokeColor(binding.picker.getColor());
        });

        SharedPreferences sharedPreferences = requireActivity().getPreferences(MODE_PRIVATE);

        cvText.setBackgroundColor(sharedPreferences.getInt("lesson_background_color",
                ContextCompat.getColor(requireContext(), R.color.yellow_bg)));
        cvText.setTextColor(sharedPreferences.getInt("lesson_text_color",
                ContextCompat.getColor(requireContext(), R.color.brown)));
        binding.sampleTextCv.setStrokeColor(ColorStateList.valueOf(
                sharedPreferences.getInt("lesson_text_color",
                        ContextCompat.getColor(requireContext(), R.color.brown))));

        binding.saveBtn.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("lesson_background_color",
                    ((ColorDrawable) binding.sampleTextCv.getBackground()).getColor());
            editor.putInt("lesson_text_color", cvText.getCurrentTextColor());
            editor.apply();
        });


        return binding.getRoot();
    }
}
