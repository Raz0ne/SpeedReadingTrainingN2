package com.application.ui.fragments.navigation.account

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.google.firebase.auth.*
import com.application.databinding.FragmentPasswordResettingBinding
import com.application.ui.fragments.auth.listeners.AuthTextWatcher
import com.application.ui.fragments.auth.listeners.PasswordOnClickListener
import com.application.R

class PasswordResettingFragment : Fragment() {

    private lateinit var binding: FragmentPasswordResettingBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentPasswordResettingBinding
            .inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed() }

        binding.emailEt.addTextChangedListener(AuthTextWatcher(binding.emailTv))
        binding.passwordEt.addTextChangedListener(AuthTextWatcher(binding.passwordTv))
        binding.newPasswordEt.addTextChangedListener(AuthTextWatcher(binding.newPasswordTv))
        binding.newPasswordRepeatingEt.addTextChangedListener(
            AuthTextWatcher(binding.newPasswordRepeatingTv))

        binding.passwordVisibilityBtn.setOnClickListener(
            PasswordOnClickListener(binding.passwordEt))
        binding.newPasswordVisibilityBtn.setOnClickListener(
            PasswordOnClickListener(binding.newPasswordEt))
        binding.newPasswordRepeatingVisibilityBtn.setOnClickListener(
            PasswordOnClickListener(binding.newPasswordRepeatingEt))

        binding.continueBtn.setOnClickListener { reAuthUser() }
    }

    private fun reAuthUser() {
        hideContinueBtn()

        val email = binding.emailEt.text.toString()
        val password = binding.passwordEt.text.toString()
        val newPassword = binding.newPasswordEt.text.toString()
        val newPasswordRepeating = binding.newPasswordRepeatingEt.text.toString()

        if (email.isEmpty()) {
            binding.emailEt.error = getString(R.string.auth_error_email_empty)
            showContinueBtn()
        }
        else if (password.isEmpty()) {
            binding.passwordEt.error = getString(R.string.auth_error_password_empty)
            showContinueBtn()
        }
        else if (newPassword.isEmpty()) {
            binding.newPasswordEt.error = getString(R.string.auth_error_password_empty)
            showContinueBtn()
        }
        else if (newPassword.length < 6) {
            binding.newPasswordEt.error = getString(R.string.auth_error_weak_password)
            showContinueBtn()
        }
        else if (newPassword.length > 16) {
            binding.newPasswordEt.error = getString(R.string.auth_error_password_too_strong)
            showContinueBtn()
        }
        else if (password == newPassword) {
            binding.newPasswordEt.error = getString(R.string.password_resetting_error_same_passwords)
            showContinueBtn()
        }
        else if (newPasswordRepeating.isEmpty()) {
            binding.newPasswordRepeatingEt.error = getString(R.string.auth_error_password_empty)
            showContinueBtn()
        }
        else if (newPassword != newPasswordRepeating) {
            binding.newPasswordRepeatingEt.error = getString(R.string.auth_error_passwords_not_match)
            showContinueBtn()
        }
        else {
            val credential = EmailAuthProvider.getCredential(email, password)

            user.reauthenticate(credential).addOnCompleteListener { task ->
                if (task.isSuccessful)
                    resetPassword(newPassword)
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
                        binding.passwordEt.error = getString(R.string.auth_error_password_error)
                    else
                        Log.d("razon", e.toString())

                    showContinueBtn()
                }
            }
        }
    }

    private fun resetPassword(newPassword: String) {
        user.updatePassword(newPassword).addOnCompleteListener { task ->
            showContinueBtn()

            if (task.isSuccessful)
                goToSignIn()
            else {
                val e = task.exception!!

                Log.d("razon", e.toString())
            }
        }
    }

    private fun hideContinueBtn() {
        binding.loading.visibility = View.VISIBLE
        binding.continueBtn.visibility = View.INVISIBLE
    }

    private fun showContinueBtn() {
        binding.loading.visibility = View.GONE
        binding.continueBtn.visibility = View.VISIBLE
    }

    private fun goToSignIn() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_title_password_changed)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.cancel()

                FirebaseAuth.getInstance().signOut()
                findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.action_passwordResettingFragment_to_loginFragment)
            }
            .show()
    }
}