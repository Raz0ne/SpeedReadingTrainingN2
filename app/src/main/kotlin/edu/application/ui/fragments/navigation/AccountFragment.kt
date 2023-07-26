package edu.application.ui.fragments.navigation

import android.annotation.SuppressLint
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
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.firebase.auth.FirebaseAuth
import edu.application.R
import edu.application.databinding.FragmentNavigationAccountBinding


class AccountFragment : Fragment() {

    private lateinit var binding: FragmentNavigationAccountBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentNavigationAccountBinding.inflate(inflater, container, false)
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

        binding.feedbackBtn.setOnClickListener { sendFeedback() }

        binding.licenseBtn.setOnClickListener {
            startActivity(Intent(requireContext(), OssLicensesMenuActivity::class.java))
        }

        binding.logoutBtn.setOnClickListener {
            auth.signOut()

            findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_accountFragment_to_loginFragment)
        }
    }

    private fun sendFeedback() {
        val i = Intent(Intent.ACTION_SENDTO)
        i.data = Uri.parse("mailto:" + getString(R.string.feedback_email))
        i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_subject))
        //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(Intent.createChooser(i,
                getString(R.string.feedback_email_client_choosing)))
        }
        catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(),
                getString(R.string.feedback_error_email_clients_not_installed), Toast.LENGTH_SHORT)
                .show()
        }
    }
}