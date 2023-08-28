package com.raz0ne.speedreading_training.ui.screens.main

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.mikepenz.aboutlibraries.LibsBuilder
import com.raz0ne.speedreading_training.R
import com.raz0ne.speedreading_training.databinding.FragmentAccountBinding
import com.raz0ne.speedreading_training.ui.screens.main.adapters.TextFormatter
import com.raz0ne.speedreading_training.util.cancelAlarm

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsBtn.setOnClickListener {
            findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_accountFragment_to_settingsFragment)
        }

        binding.changeEmailBtn.setOnClickListener {
            findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_accountFragment_to_emailResettingFragment)
        }

        binding.changePasswordBtn.setOnClickListener {
            findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_accountFragment_to_passwordResettingFragment)
        }

        binding.feedbackBtn.setOnClickListener { sendFeedback() }

        binding.licenseBtn.setOnClickListener {
            LibsBuilder()
                .withActivityTitle(getString(R.string.fragment_label_license))
                .withSearchEnabled(true)
                .start(requireContext())
        }

        binding.logoutBtn.setOnClickListener { signOut() }
    }

    private fun sendFeedback() {
        val i = Intent(Intent.ACTION_SENDTO)
        i.data = Uri.parse("mailto:" + getString(R.string.feedback_email))
        i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_subject))
        try {
            startActivity(Intent.createChooser(i, getString(R.string.feedback_email_client_choosing)))
        }
        catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), getString(R.string.feedback_error_email_clients_not_installed),
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun signOut() {
        TextFormatter.sharedPreferences.edit().clear().apply()
        cancelAlarm(requireContext())

        auth.signOut()

        findNavController(requireActivity(), R.id.nav_host_fragment)
            .navigate(R.id.action_accountFragment_to_loginFragment)
    }
}