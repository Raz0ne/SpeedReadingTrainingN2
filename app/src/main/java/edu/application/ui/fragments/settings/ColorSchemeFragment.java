package edu.application.ui.fragments.settings;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import edu.application.R;
import edu.application.databinding.FragmentColorsSchemeBinding;

public class ColorSchemeFragment extends Fragment {

    private FragmentColorsSchemeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentColorsSchemeBinding.inflate(inflater, container, false);

        SharedPreferences sharedPreferences = requireActivity().getPreferences(MODE_PRIVATE);

        binding.lightBtn.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("lesson_background_color");
            editor.remove("lesson_text_color");
            editor.apply();
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.action_colorSchemeFragment_to_colorSchemeFragment);
        });

        binding.customBtn.setBackgroundColor(sharedPreferences.getInt("lesson_background_color",
                ContextCompat.getColor(requireContext(), R.color.yellow_bg)));
        binding.customBtn.setTextColor(sharedPreferences.getInt("lesson_text_color",
                ContextCompat.getColor(requireContext(), R.color.brown)));
        binding.customBtn.setStrokeColor(ColorStateList.valueOf(
                sharedPreferences.getInt("lesson_text_color",
                        ContextCompat.getColor(requireContext(), R.color.brown))));

        binding.customBtn.setOnClickListener(v ->
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_colorSchemeFragment_to_colorSchemeCustomFragment));

        return binding.getRoot();
    }
}
