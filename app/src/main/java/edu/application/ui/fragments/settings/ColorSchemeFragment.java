package edu.application.ui.fragments.settings;

import static android.content.Context.MODE_PRIVATE;

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
import edu.application.databinding.FragmentColorsSchemeBinding;
import edu.application.ui.adapters.TextFormatter;

public class ColorSchemeFragment extends Fragment {

    private FragmentColorsSchemeBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentColorsSchemeBinding.inflate(inflater, container, false);

        sharedPreferences = requireActivity().getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();

        TextFormatter.setColor(binding.customBtn);
        TextFormatter.setBorder(binding.customBtn);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.lightBtn.setOnClickListener(v -> {
            editor.remove("lesson_background_color");
            editor.remove("lesson_text_color");
            editor.apply();
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.action_colorSchemeFragment_to_colorSchemeFragment);
        });

        binding.customBtn.setOnClickListener(v ->
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_colorSchemeFragment_to_colorSchemeCustomFragment));
    }
}
