package com.application.ui.fragments.auth

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.application.R
import com.application.databinding.FragmentAuthLoginBinding
import com.application.ui.fragments.auth.listeners.AuthTextWatcher
import com.application.ui.fragments.auth.listeners.PasswordOnClickListener

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentAuthLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentAuthLoginBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disableBackPress(viewLifecycleOwner)

        binding.emailEt.addTextChangedListener(AuthTextWatcher(binding.emailTv))
        binding.passwordEt.addTextChangedListener(AuthTextWatcher(binding.passwordTv))

        binding.passwordVisibilityBtn.setOnClickListener(
            PasswordOnClickListener(binding.passwordEt))

        binding.signInBtn.setOnClickListener { signInUser() }
        binding.registerBtn.setOnClickListener {
            findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_loginFragment_to_registerFragment) }
        binding.resetPassword.setOnClickListener {resetPassword() }
    }

    private fun resetPassword() {
        hideSignInBtn()

        val email = binding.emailEt.text.toString()

        if (email.isEmpty()) {
            binding.emailEt.error = getString(R.string.auth_error_email_empty)
            showSignInBtn()
        }
        else
            auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                showSignInBtn()

                if (task.isSuccessful)
                    showDialog()
                else {
                    val e = task.exception!!

                    if (e is FirebaseAuthInvalidCredentialsException &&
                        e.errorCode == "ERROR_INVALID_EMAIL")
                        binding.emailEt.error = getString(R.string.auth_error_email_incorrect)
                    else if (e is FirebaseAuthInvalidUserException &&
                        e.errorCode == "ERROR_USER_NOT_FOUND")
                        binding.emailEt.error = getString(R.string.auth_error_email_not_found)
                    else
                        Log.d("razon", e.toString())

                }
            }
    }

    private fun signInUser() {
        hideSignInBtn()

        val email = binding.emailEt.text.toString()
        val password = binding.passwordEt.text.toString()

        if (email.isEmpty()) {
            binding.emailEt.error = getString(R.string.auth_error_email_empty)
            showSignInBtn()
        }
        else if (password.isEmpty()) {
            binding.passwordEt.error = getString(R.string.auth_error_password_empty)
            showSignInBtn()
        }
        else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (auth.currentUser!!.isEmailVerified)
                        findNavController(requireActivity(), R.id.nav_host_fragment)
                            .navigate(R.id.action_loginFragment_to_trainingFragment)
                    else {
                        binding.emailEt.error = getString(R.string.auth_error_email_not_verified)

                        auth.signOut()

                        showSignInBtn()
                    }
                }
                else {
                    val e = task.exception!!

                    if (e is FirebaseAuthInvalidCredentialsException &&
                        e.errorCode == "ERROR_INVALID_EMAIL")
                        binding.emailEt.error = getString(R.string.auth_error_email_incorrect)
                    else if (e is FirebaseAuthInvalidUserException &&
                        e.errorCode == "ERROR_USER_NOT_FOUND")
                        binding.emailEt.error = getString(R.string.auth_error_email_not_found)
                    else if (e is FirebaseAuthInvalidCredentialsException &&
                        e.errorCode == "ERROR_WRONG_PASSWORD")
                        binding.passwordEt.error = getString(
                                R.string.auth_error_password_error)
                    else
                        Log.d("razon", e.toString())

                    auth.signOut()

                    showSignInBtn()
                }
            }
        }
    }

    private fun disableBackPress(lifecycleOwner: LifecycleOwner) {
        requireActivity().onBackPressedDispatcher.addCallback(lifecycleOwner,
            object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {}
            }
        )
    }

    private fun hideSignInBtn() {
        binding.loading.visibility = View.VISIBLE
        binding.signInBtn.visibility = View.INVISIBLE
    }

    private fun showSignInBtn() {
        binding.loading.visibility = View.GONE
        binding.signInBtn.visibility = View.VISIBLE
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_title_password_reset)
            .setMessage(getString(R.string.dialog_desc_follow_instruction) + ' ' +
                    binding.emailEt.text)
            .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.cancel() }
            .show()
    }
}