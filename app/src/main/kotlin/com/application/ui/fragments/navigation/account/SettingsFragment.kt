package com.application.ui.fragments.navigation.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.application.R
import com.application.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.colorSchemeBtn.setOnClickListener {
            findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_settingsFragment_to_colorSchemeFragment) }
        binding.fontSettingsBtn.setOnClickListener {
            findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_settingsFragment_to_fontSettingsFragment) }
    }
}