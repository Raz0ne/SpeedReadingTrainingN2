package edu.application.ui.fragments.auth

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
import edu.application.R
import edu.application.databinding.FragmentAuthRegisterBinding
import edu.application.ui.fragments.auth.listeners.AuthTextWatcher
import edu.application.ui.fragments.auth.listeners.PasswordOnClickListener

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
            AuthTextWatcher(binding.passwordRepeatingTv)
        )

        binding.passwordVisibilityBtn.setOnClickListener(
            PasswordOnClickListener(binding.passwordEt)
        )
        binding.passwordRepeatingVisibilityBtn.setOnClickListener(
            PasswordOnClickListener(binding.passwordRepeatingEt)
        )

        binding.signUpBtn.setOnClickListener { signUpUser() }

        binding.signInBtn.setOnClickListener {
            findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_registerFragment_to_loginFragment) }
    }

    private fun signUpUser() {
        binding.errorTv.visibility = View.INVISIBLE

        val email = binding.emailEt.text.toString()
        val password = binding.passwordEt.text.toString()

        if (email.isEmpty())
            binding.errorTv.error = getString(R.string.register_error_email_empty)
        else if (password.isEmpty())
            binding.errorTv.error = getString(R.string.register_error_password_empty)
        else if (password.length < 6)
            binding.errorTv.error = getString(R.string.register_error_weak_password)
        else if (password.length > 16)
            binding.errorTv.error = getString(R.string.register_error_password_too_long)
        else if (password != binding.passwordRepeatingEt.text.toString())
            binding.errorTv.error = getString(R.string.register_error_passwords_not_match)
        else {
            binding.errorTv.visibility = View.INVISIBLE
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful)
                        findNavController(requireActivity(), R.id.nav_host_fragment)
                            .navigate(R.id.action_registerFragment_to_trainingFragment)
                    else {
                        val e = task.exception!!

                        if (e is FirebaseAuthInvalidCredentialsException &&
                            e.errorCode == "ERROR_INVALID_EMAIL")
                            binding.errorTv.error = getString(
                                R.string.register_error_email_incorrect
                            )
                        else if (e is FirebaseAuthUserCollisionException)
                            binding.errorTv.error = getString(
                                R.string.register_error_email_already_in_use
                            )
                        else
                            Log.d("razon", e.toString())
                    }
                }
        }

        binding.errorTv.visibility = View.VISIBLE
    }
}