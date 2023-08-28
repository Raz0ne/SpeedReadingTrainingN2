package com.raz0ne.speedreading_training.ui.fragments.screens.account

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.google.firebase.auth.*
import com.raz0ne.speedreading_training.databinding.FragmentEmailResettingBinding
import com.raz0ne.speedreading_training.ui.fragments.auth.listeners.AuthTextWatcher
import com.raz0ne.speedreading_training.ui.fragments.auth.listeners.PasswordOnClickListener
import com.raz0ne.speedreading_training.R

class EmailResettingFragment : Fragment() {

    private lateinit var binding: FragmentEmailResettingBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentEmailResettingBinding
            .inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.emailEt.addTextChangedListener(AuthTextWatcher(binding.emailTv))
        binding.newEmailEt.addTextChangedListener(AuthTextWatcher(binding.newEmailTv))
        binding.passwordEt.addTextChangedListener(AuthTextWatcher(binding.passwordTv))

        binding.passwordVisibilityBtn.setOnClickListener(
            PasswordOnClickListener(binding.passwordEt))

        binding.continueBtn.setOnClickListener { reAuthUser() }
    }

    private fun reAuthUser() {
        hideContinueBtn()

        val email = binding.emailEt.text.toString()
        val password = binding.passwordEt.text.toString()
        val newEmail = binding.newEmailEt.text.toString()

        if (email.isEmpty()) {
            binding.emailEt.error = getString(R.string.auth_error_email_empty)
            showContinueBtn()
        }
        else if (password.isEmpty()) {
            binding.passwordEt.error = getString(R.string.auth_error_password_empty)
            showContinueBtn()
        }
        else if (newEmail.isEmpty()) {
            binding.newEmailEt.error = getString(R.string.auth_error_email_empty)
            showContinueBtn()
        }
        else if (email == newEmail) {
            binding.newEmailEt.error = getString(R.string.email_resetting_error_same_emails)
            showContinueBtn()
        }
        else {
            val credential = EmailAuthProvider.getCredential(email, password)

            user.reauthenticate(credential).addOnCompleteListener { task ->
                if (task.isSuccessful)
                    resetEmail(newEmail)
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

    private fun resetEmail(newEmail: String) {
        user.updateEmail(newEmail).addOnCompleteListener { task ->
            showContinueBtn()

            if (task.isSuccessful)
                goToSignIn()
            else {
                val e = task.exception!!

                if (e is FirebaseAuthInvalidCredentialsException &&
                    e.errorCode == "ERROR_INVALID_EMAIL")
                    binding.newEmailEt.error = getString(R.string.auth_error_email_incorrect)
                else if (e is FirebaseAuthUserCollisionException)
                    binding.emailEt.error = getString(
                        R.string.auth_error_email_already_in_use)
                else
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
            .setTitle(R.string.dialog_title_email_changed)
            .setMessage(getString(R.string.dialog_desc_follow_instruction) + ' ' + binding.emailEt.text)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.cancel()

                FirebaseAuth.getInstance().signOut()
                findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.action_emailResettingFragment_to_loginFragment)
            }
            .show()
    }
}