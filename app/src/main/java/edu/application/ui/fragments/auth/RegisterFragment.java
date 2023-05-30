package edu.application.ui.fragments.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import edu.application.R;
import edu.application.databinding.FragmentRegisterBinding;
import edu.application.ui.listeners.AuthTextWatcher;
import edu.application.ui.listeners.PasswordOnClickListener;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.emailEt.addTextChangedListener(new AuthTextWatcher(binding.emailTv));
        binding.passwordEt.addTextChangedListener(new AuthTextWatcher(binding.passwordTv));
        binding.passwordRepeatingEt.addTextChangedListener(
                new AuthTextWatcher(binding.passwordRepeatingTv));

        binding.passwordVisibilityBtn.setOnClickListener(
                new PasswordOnClickListener(binding.passwordEt));
        binding.passwordRepeatingVisibilityBtn.setOnClickListener(
                new PasswordOnClickListener(binding.passwordRepeatingEt));

        binding.signUpBtn.setOnClickListener(v -> signUpUser());

        binding.signInBtn.setOnClickListener(v ->
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_registerFragment_to_loginFragment));
    }

    private void signUpUser() {
        String email = binding.emailEt.getText().toString();
        String password = binding.passwordEt.getText().toString();

        if (email.isEmpty())
            binding.errorTv.setText(getString(R.string.register_error_email_empty));
        else if (password.isEmpty())
            binding.errorTv.setText(getString(R.string.register_error_password_empty));
        else if (password.length() < 6)
            binding.errorTv.setText(getString(R.string.register_error_password_too_short));
        else if (password.length() > 18)
            binding.errorTv.setText(getString(R.string.register_error_password_too_long));
        else if (!password.equals(binding.passwordRepeatingEt.getText().toString()))
            binding.errorTv.setText(getString(R.string.register_error_passwords_not_match));
        else {
            binding.errorTv.setVisibility(View.INVISIBLE);
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(task ->
                            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                                    .navigate(R.id.action_registerFragment_to_trainingFragment))
                    .addOnFailureListener(e -> {
                        if (e instanceof FirebaseAuthInvalidCredentialsException &&
                                ((FirebaseAuthInvalidCredentialsException) e).getErrorCode()
                                        .equals("ERROR_INVALID_EMAIL"))
                            binding.errorTv.setText(getString(
                                    R.string.register_error_email_incorrect));
                        else if (e instanceof FirebaseAuthUserCollisionException)
                            binding.errorTv.setText(getString(
                                    R.string.register_error_email_already_in_use));
                        else
                            Log.d("razon", e.toString());
                    });
        }

        binding.errorTv.setVisibility(View.VISIBLE);
    }
}
