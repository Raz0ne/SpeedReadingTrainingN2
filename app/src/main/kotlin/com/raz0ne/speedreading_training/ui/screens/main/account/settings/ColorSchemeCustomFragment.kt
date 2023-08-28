package com.raz0ne.speedreading_training.ui.screens.main.account.settings

import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.raz0ne.speedreading_training.databinding.FragmentCustomColorsSchemeBinding
import com.raz0ne.speedreading_training.ui.screens.main.adapters.TextFormatter.setBorder
import com.raz0ne.speedreading_training.ui.screens.main.adapters.TextFormatter.setColor
import com.raz0ne.speedreading_training.ui.screens.main.adapters.TextFormatter.setTextSize
import com.raz0ne.speedreading_training.ui.screens.main.adapters.TextFormatter.sharedPreferences

class ColorSchemeCustomFragment : Fragment() {

    private lateinit var binding: FragmentCustomColorsSchemeBinding
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentCustomColorsSchemeBinding.inflate(inflater, container, false)
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