package edu.application.ui.fragments.auth;

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
import edu.application.databinding.FragmentLoginBinding;
import edu.application.ui.listeners.AuthTextWatcher;
import edu.application.ui.listeners.PasswordOnClickListener;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    private String emailNeed = "1234", passwordNeed = "1234", email, password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);

        binding.emailEt.addTextChangedListener(new AuthTextWatcher(binding.emailTv));
        binding.passwordEt.addTextChangedListener(new AuthTextWatcher(binding.passwordTv));

        binding.passwordVisibilityBtn.setOnClickListener(
                new PasswordOnClickListener(binding.passwordEt));

        binding.enterBtn.setOnClickListener(v -> {
            email = binding.emailEt.getText().toString();
            password = binding.passwordEt.getText().toString();

            if (email.equals(emailNeed) && password.equals(passwordNeed)) {
                SharedPreferences sharedPreferences =
                        requireActivity().getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("auth", 1);
                editor.apply();

                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_loginFragment_to_trainingFragment);
            }
            else
                binding.passwordErrorTv.setVisibility(View.VISIBLE);
        });

        binding.registerBtn.setOnClickListener(v->
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_loginFragment_to_registerFragment));

        return binding.getRoot();
    }
}
