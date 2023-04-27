package edu.application.ui.fragments.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import edu.application.R;
import edu.application.databinding.FragmentRegisterBinding;
import edu.application.ui.listeners.AuthTextWatcher;
import edu.application.ui.listeners.PasswordOnClickListener;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        binding.emailEt.addTextChangedListener(new AuthTextWatcher(binding.emailTv));
        binding.passwordEt.addTextChangedListener(new AuthTextWatcher(binding.passwordTv));
        binding.passwordRepeatingEt.addTextChangedListener(
                new AuthTextWatcher(binding.passwordRepeatingTv));

        binding.passwordVisibilityBtn.setOnClickListener(
                new PasswordOnClickListener(binding.passwordEt));
        binding.passwordRepeatingVisibilityBtn.setOnClickListener(
                new PasswordOnClickListener(binding.passwordRepeatingEt));

        binding.enterBtn.setOnClickListener(v ->
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_registerFragment_to_loginFragment));

        return binding.getRoot();
    }
}
