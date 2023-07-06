package edu.application.ui.fragments.settings

import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.application.databinding.FragmentColorsSchemeCustomBinding
import edu.application.ui.adapters.TextFormatter.setBorder
import edu.application.ui.adapters.TextFormatter.setColor
import edu.application.ui.adapters.TextFormatter.setTextSize
import edu.application.ui.adapters.TextFormatter.sharedPreferences

class ColorSchemeCustomFragment : Fragment() {

    private lateinit var binding: FragmentColorsSchemeCustomBinding
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentColorsSchemeCustomBinding.inflate(inflater, container, false)
        editor = sharedPreferences.edit()

        binding.picker.addSVBar(binding.svBar)
        binding.picker.addOpacityBar(binding.opacityBar)

        setColor(binding.cvText)
        setBorder(binding.sampleTextCv)
        setTextSize(binding.cvText)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener { requireActivity().onBackPressed() }

        binding.setBackgroundColorBtn.setOnClickListener {
            binding.picker.oldCenterColor = binding.picker.color
            binding.cvText.setBackgroundColor(binding.picker.oldCenterColor)
        }
        binding.setTextColorBtn.setOnClickListener {
            binding.picker.oldCenterColor = binding.picker.color
            binding.cvText.setTextColor(binding.picker.color)
            binding.sampleTextCv.strokeColor = binding.picker.color
        }

        binding.saveBtn.setOnClickListener {
            editor.putInt("lesson_background_color",
                (binding.cvText.background as ColorDrawable).color)
            editor.putInt("lesson_text_color", binding.cvText.currentTextColor)
            editor.apply()
        }
    }
}