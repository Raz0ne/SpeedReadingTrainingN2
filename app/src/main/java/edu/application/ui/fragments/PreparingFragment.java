package edu.application.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import edu.application.R;
import edu.application.databinding.FragmentPreparingBinding;

public class PreparingFragment extends Fragment {

    private Handler handler;

    private FragmentPreparingBinding binding;

    class Task implements Runnable {
        int number;
        Task (int number) { this.number = number; }

        @Override
        public void run() {
            if (getActivity() == null)
                return;

            if (number > 0) {
                binding.digit.setText(String.valueOf(number));
                handler.postDelayed(new PreparingFragment.Task(number - 1), 1000);
            }
            else if (number == 0) {
                binding.digit.setText("0");
                handler.postDelayed(new PreparingFragment.Task(number - 1), 500);
            }
            else
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_preparingFragment_to_lessonReadingTextFragment,
                                getArguments());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentPreparingBinding.inflate(inflater, container, false);

        handler = new Handler();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        handler.post(new PreparingFragment.Task(3));
    }
}
