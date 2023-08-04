package com.application.ui.fragments.auth

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.application.R
import com.application.databinding.FragmentAuthRegisterBinding
import com.application.ui.fragments.auth.listeners.AuthTextWatcher
import com.application.ui.fragments.auth.listeners.PasswordOnClickListener

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentAuthRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentAuthRegisterBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.emailEt.addTextChangedListener(AuthTextWatcher(binding.emailTv))
        binding.passwordEt.addTextChangedListener(AuthTextWatcher(binding.passwordTv))
        binding.passwordRepeatingEt.addTextChangedListener(
            AuthTextWatcher(binding.passwordRepeatingTv))

        binding.passwordVisibilityBtn.setOnClickListener(
            PasswordOnClickListener(binding.passwordEt))
        binding.passwordRepeatingVisibilityBtn.setOnClickListener(
            PasswordOnClickListener(binding.passwordRepeatingEt))

        binding.signUpBtn.setOnClickListener { signUpUser() }

        binding.signInBtn.setOnClickListener {
            findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_registerFragment_to_loginFragment) }
    }

    private fun signUpUser() {
        hideSignUpBtn()

        val email = binding.emailEt.text.toString()
        val password = binding.passwordEt.text.toString()

        if (email.isEmpty()) {
            binding.emailEt.error = getString(R.string.auth_error_email_empty)
            showSignUpBtn()
        }
        else if (password.isEmpty()) {
            binding.passwordEt.error = getString(R.string.auth_error_password_empty)
            showSignUpBtn()
        }
        else if (password.length < 6) {
            binding.passwordEt.error = getString(R.string.auth_error_weak_password)
            showSignUpBtn()
        }
        else if (password.length > 16) {
            binding.passwordEt.error = getString(R.string.auth_error_password_too_strong)
            showSignUpBtn()
        }
        else if (password != binding.passwordRepeatingEt.text.toString()) {
            binding.passwordRepeatingEt.error = getString(R.string.auth_error_passwords_not_match)
            showSignUpBtn()
        }
        else {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                showSignUpBtn()

                if (task.isSuccessful)
                    goToSignIn()
                else {
                    val e = task.exception!!

                    if (e is FirebaseAuthInvalidCredentialsException &&
                        e.errorCode == "ERROR_INVALID_EMAIL")
                        binding.emailEt.error = getString(
                                R.string.auth_error_email_incorrect)
                    else if (e is FirebaseAuthUserCollisionException)
                        binding.emailEt.error = getString(
                                R.string.auth_error_email_already_in_use)
                    else
                        Log.d("razon", e.toString())
                }
            }
        }
    }

    private fun hideSignUpBtn() {
        binding.loading.visibility = View.VISIBLE
        binding.signUpBtn.visibility = View.INVISIBLE
    }

    private fun showSignUpBtn() {
        binding.loading.visibility = View.GONE
        binding.signUpBtn.visibility = View.VISIBLE
    }

    private fun goToSignIn() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_title_email_verification)
            .setMessage(getString(R.string.dialog_desc_follow_instruction) + ' ' + binding.emailEt.text)
            .setPositiveButton(R.string.dialog_pos_text_ok) { dialog, _ ->
                dialog.cancel()

                findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.action_registerFragment_to_loginFragment)
            }
            .show()
    }
}