package edu.application.ui.fragments.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import edu.application.R
import edu.application.databinding.FragmentLoginBinding
import edu.application.ui.listeners.AuthTextWatcher
import edu.application.ui.listeners.PasswordOnClickListener

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.emailEt.addTextChangedListener(AuthTextWatcher(binding.emailTv))
        binding.passwordEt.addTextChangedListener(AuthTextWatcher(binding.passwordTv))

        binding.passwordVisibilityBtn.setOnClickListener(
            PasswordOnClickListener(binding.passwordEt))
        binding.signInBtn.setOnClickListener { signInUser() }
        binding.registerBtn.setOnClickListener {
            findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_loginFragment_to_registerFragment) }
    }

    private fun signInUser() {
        val email = binding.emailEt.text.toString()
        val password = binding.passwordEt.text.toString()

        if (email.isEmpty())
            binding.errorTv.text = getString(R.string.login_error_email_empty)
        else if (password.isEmpty())
            binding.errorTv.text = getString(R.string.login_error_password_empty)
        else {
            binding.errorTv.visibility = View.INVISIBLE
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.action_loginFragment_to_trainingFragment) }
                .addOnFailureListener { e: Exception ->
                    if (e is FirebaseAuthInvalidCredentialsException &&
                        e.errorCode == "ERROR_INVALID_EMAIL")
                        binding.errorTv.text = getString(R.string.login_error_email_incorrect)
                    else if (e is FirebaseAuthInvalidUserException &&
                        e.errorCode == "ERROR_USER_NOT_FOUND")
                        binding.errorTv.text = getString(R.string.login_error_email_not_found)
                    else if (e is FirebaseAuthInvalidCredentialsException &&
                        e.errorCode == "ERROR_WRONG_PASSWORD")
                        binding.errorTv.text = getString(
                        R.string.login_error_password_error
                    )
                    else
                        Log.d("razon", e.toString())
                }
        }

        binding.errorTv.visibility = View.VISIBLE
    }
}