package com.application.ui.fragments.navigation.account

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.graphics.Bitmap.Config
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.application.R
import com.application.databinding.FragmentSettingsBinding
import java.util.Locale


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
        binding.notificationBtn.setOnClickListener {}
        binding.languageBtn.setOnClickListener { chooseLanguage() }
    }

    private fun chooseLanguage() {
        val dict = resources.getStringArray(R.array.dialog_items_languages)
        dict[0] += ":sys"
        val languages = Array(dict.size) { "" }
        val langCodes = Array(dict.size) { "" }

        for (idx in dict.indices) {
            dict[idx].split(':').let {
                languages[idx] = it[0]
                langCodes[idx] = it[1]
            }
        }

        AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_title_language_choosing)
            .setSingleChoiceItems(languages, 0) { dialogInterface, i ->
                setLocale(langCodes[i])

                requireActivity().recreate()
                dialogInterface.dismiss()
            }
            //.setItems(languages) { _, which -> setLocale(langCodes[which]) }
            .show()
    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        requireContext().resources.configuration.setLocale(locale)

        val config = Configuration()
        config.setLocale(locale)
        requireActivity().baseContext.createConfigurationContext(config)
    }
}