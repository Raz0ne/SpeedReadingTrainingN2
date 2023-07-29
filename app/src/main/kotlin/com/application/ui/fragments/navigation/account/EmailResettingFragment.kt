package com.application.ui.fragments.navigation.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.google.firebase.auth.*
import com.application.databinding.FragmentAccountEmailResettingBinding
import com.application.ui.fragments.auth.listeners.AuthTextWatcher
import com.application.ui.fragments.auth.listeners.PasswordOnClickListener
import com.application.R

class EmailResettingFragment : Fragment() {

    private lateinit var binding: FragmentAccountEmailResettingBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentAccountEmailResettingBinding.inflate(inflater, container, false)
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
            PasswordOnClickListener(binding.passwordEt)
        )

        binding.continueBtn.setOnClickListener { reAuthUser() }
    }

    private fun reAuthUser() {
        binding.errorTv.visibility = View.INVISIBLE

        val email = binding.emailEt.text.toString()
        val password = binding.passwordEt.text.toString()

        val credential = EmailAuthProvider
            .getCredential(email, password)

        if (email.isEmpty())
            binding.errorTv.error = getString(R.string.login_error_email_empty)
        else if (password.isEmpty())
            binding.errorTv.error = getString(R.string.login_error_password_empty)
        else {
            binding.errorTv.visibility = View.INVISIBLE
            user.reauthenticate(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful)
                        resetEmail()
                    else {
                        val e = task.exception!!

                        if (e is FirebaseAuthInvalidCredentialsException &&
                            e.errorCode == "ERROR_INVALID_EMAIL")
                            binding.errorTv.error = getString(R.string.login_error_email_incorrect)
                        else if (e is FirebaseAuthInvalidUserException &&
                            e.errorCode == "ERROR_USER_NOT_FOUND")
                            binding.errorTv.error = getString(R.string.login_error_email_not_found)
                        else if (e is FirebaseAuthInvalidCredentialsException &&
                            e.errorCode == "ERROR_WRONG_PASSWORD")
                            binding.errorTv.error = getString(R.string.login_error_password_error)
                        else
                            Log.d("razon", e.toString())
                    }
                }
        }

        binding.errorTv.visibility = View.VISIBLE
    }

    private fun resetEmail() {
        val newEmail = binding.newEmailEt.text.toString()

        user.updateEmail(newEmail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_emailResettingFragment_to_accountFragment)
                else {
                    val e = task.exception!!

                    if (e is FirebaseAuthInvalidCredentialsException &&
                        e.errorCode == "ERROR_INVALID_EMAIL")
                        binding.errorTv.error = getString(R.string.login_error_email_incorrect)
                    else
                        Log.d("razon", e.toString())
                }
            }
    }
}