package com.raz0ne.speedreading_training.ui.fragments.screens.account

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import android.Manifest.permission.POST_NOTIFICATIONS
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.raz0ne.speedreading_training.R
import com.raz0ne.speedreading_training.databinding.FragmentSettingsBinding
import com.raz0ne.speedreading_training.ui.activities.MainActivity
import com.raz0ne.speedreading_training.ui.fragments.screens.account.settings.ReminderDialog
import com.raz0ne.speedreading_training.ui.fragments.screens.adapters.TextFormatter


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
        binding.reminderBtn.setOnClickListener { showReminderDialog() }
        binding.languageBtn.setOnClickListener { chooseLanguage() }
    }

    private fun chooseLanguage() {
        val dict = resources.getStringArray(R.array.dialog_items_languages)
        dict[0] += ":sys"
        val languages = Array(dict.size) { "" }
        val langCodes = Array(dict.size) { "" }

        for (idx in dict.indices)
            dict[idx].split(':').let {
                languages[idx] = it[0]
                langCodes[idx] = it[1]
            }

        val curLangIdx = TextFormatter.sharedPreferences.getString("language", "sys")!!.let {
            langCodes.indexOf(it)
        }

        AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_title_language_choosing)
            .setSingleChoiceItems(languages, curLangIdx) { dialogInterface, i ->
                if (i != curLangIdx)
                    setLocale(langCodes[i])

                dialogInterface.dismiss()
            }
            .show()
    }

    private fun setLocale(lang: String) {
        val editor = TextFormatter.sharedPreferences.edit()
        editor.putString("language", lang)
        editor.apply()

        requireActivity().recreate()
    }

    private fun showReminderDialog() {
        if (android.os.Build.VERSION.SDK_INT >= 33) {
            if((requireActivity() as MainActivity).checkPermission(POST_NOTIFICATIONS))
                ReminderDialog(requireContext()).show()
            else if (shouldShowRequestPermissionRationale(POST_NOTIFICATIONS))
                Toast.makeText(requireContext(), R.string.toast_permission_denied, LENGTH_SHORT).show()
        }
        else
            ReminderDialog(requireContext()).show()
    }
}