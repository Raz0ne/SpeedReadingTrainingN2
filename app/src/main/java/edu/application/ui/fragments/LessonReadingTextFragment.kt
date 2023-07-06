package edu.application.ui.fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;

import edu.application.R;
import edu.application.databinding.FragmentLessonReadingTextBinding;
import edu.application.ui.adapters.TextFormatter;

public class LessonReadingTextFragment extends Fragment {

    private FragmentLessonReadingTextBinding binding;
    private ArrayList<Integer> colors;
    private ArrayList<Integer> wordsIndexes;
    private Spannable textSpannable;
    private Handler handler;

    class Task implements Runnable {
        int idx;
        Task (int idx) { this.idx = idx; }

        @Override
        public void run() {
            if (getActivity() == null)
                return;

            for (int d = -4; d <= 4; d++) {
                try {
                    textSpannable.setSpan(new ForegroundColorSpan(colors.get(Math.abs(d))),
                            wordsIndexes.get(idx + d), wordsIndexes.get(idx + d + 1),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } catch (IndexOutOfBoundsException ignored) {}
            }
            binding.text.setText(textSpannable);

            if (idx < wordsIndexes.size() - 1)
                handler.postDelayed(new Task(idx + 1), 100);
            else
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(
                        R.id.action_lessonReadingTextFragment_to_lessonReadingQuestionsFragment,
                        getArguments());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLessonReadingTextBinding.inflate(inflater, container, false);
        assert getArguments() != null;
        binding.text.setText(getArguments().getString("text"));
        TextFormatter.setTextSettings(binding.text);
        binding.text.setMovementMethod(new ScrollingMovementMethod());

        int textColor = binding.text.getCurrentTextColor();
        int backgroundColor = ((ColorDrawable) binding.text.getBackground()).getColor();
        binding.getRoot().setBackgroundColor(backgroundColor);

        colors = new ArrayList<>();
        colors.add(textColor);
        colors.add(ColorUtils.blendARGB(textColor, backgroundColor, 0.15F));
        colors.add(ColorUtils.blendARGB(textColor, backgroundColor, 0.3F));
        colors.add(ColorUtils.blendARGB(textColor, backgroundColor, 0.45F));
        colors.add(ColorUtils.blendARGB(textColor, backgroundColor, 0.5F));

        binding.text.setTextColor(colors.get(4));

        String text = getArguments().getString("text");
        textSpannable = new SpannableString(text);

        wordsIndexes = new ArrayList<>();
        wordsIndexes.add(0);
        int index = text.indexOf(' ');
        while (index >= 0) {
            wordsIndexes.add(index + 1);
            index = text.indexOf(' ', index + 1);
        }
        wordsIndexes.add(text.length() - 1);

        handler = new Handler();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.post(new Task(0));
    }
}