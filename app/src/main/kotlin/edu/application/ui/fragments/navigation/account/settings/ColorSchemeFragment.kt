package edu.application.ui.fragments.navigation.account.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import edu.application.R
import edu.application.databinding.FragmentSettingsColorsSchemeBinding
import edu.application.ui.fragments.navigation.adapters.TextFormatter.setBorder
import edu.application.ui.fragments.navigation.adapters.TextFormatter.setColor
import edu.application.ui.fragments.navigation.adapters.TextFormatter.sharedPreferences

class ColorSchemeFragment : Fragment() {

    private lateinit var binding: FragmentSettingsColorsSchemeBinding
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentSettingsColorsSchemeBinding.inflate(inflater, container, false)
        editor = sharedPreferences.edit()

        setColor(binding.customBtn)
        setBorder(binding.customBtn)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lightBtn.setOnClickListener {
            editor.remove("lesson_background_color")
            editor.remove("lesson_text_color")
            editor.apply()
            findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_colorSchemeFragment_to_colorSchemeFragment)
        }

        binding.customBtn.setOnClickListener {
            findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_colorSchemeFragment_to_colorSchemeCustomFragment) }
    }
}