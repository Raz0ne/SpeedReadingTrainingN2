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
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import edu.application.R;
import edu.application.databinding.FragmentLoginBinding;
import edu.application.ui.listeners.AuthTextWatcher;
import edu.application.ui.listeners.PasswordOnClickListener;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.passwordEt.addTextChangedListener(new AuthTextWatcher(binding.passwordTv));

        binding.passwordVisibilityBtn.setOnClickListener(
                new PasswordOnClickListener(binding.passwordEt));

        binding.signInBtn.setOnClickListener(v -> signInUser());

        binding.registerBtn.setOnClickListener(v->
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_loginFragment_to_registerFragment));
    }

    private void signInUser() {
        String email = binding.emailEt.getText().toString();
        String password = binding.passwordEt.getText().toString();

        if (email.isEmpty())
            binding.errorTv.setText(getString(R.string.login_error_email_empty));
        else if (password.isEmpty())
            binding.errorTv.setText(getString(R.string.login_error_password_empty));
        else {
            binding.errorTv.setVisibility(View.INVISIBLE);
            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(task ->
                            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                                    .navigate(R.id.action_loginFragment_to_trainingFragment))
                    .addOnFailureListener(e -> {
                        if (e instanceof FirebaseAuthInvalidCredentialsException &&
                                ((FirebaseAuthInvalidCredentialsException) e).getErrorCode()
                                        .equals("ERROR_INVALID_EMAIL"))
                            binding.errorTv.setText(getString(
                                    R.string.login_error_email_incorrect));
                        else if (e instanceof FirebaseAuthInvalidUserException &&
                                ((FirebaseAuthInvalidUserException) e).getErrorCode()
                                        .equals("ERROR_USER_NOT_FOUND"))
                            binding.errorTv.setText(getString(
                                    R.string.login_error_email_not_found));
                        else
                            Log.d("razon", e.toString());
                    });
        }

        binding.errorTv.setVisibility(View.VISIBLE);
    }
}
