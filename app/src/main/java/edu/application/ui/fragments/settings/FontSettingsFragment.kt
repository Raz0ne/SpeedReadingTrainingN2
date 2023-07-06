package edu.application.ui.fragments.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import com.google.android.material.slider.Slider
import edu.application.R
import edu.application.databinding.FragmentFontSettingsBinding
import edu.application.ui.adapters.TextFormatter.booleanToJustificationMode
import edu.application.ui.adapters.TextFormatter.setBorder
import edu.application.ui.adapters.TextFormatter.setTextSettings
import edu.application.ui.adapters.TextFormatter.sharedPreferences

class FontSettingsFragment : Fragment() {

    private lateinit var binding: FragmentFontSettingsBinding
    private lateinit var editor: SharedPreferences.Editor

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentFontSettingsBinding.inflate(inflater, container, false)
        editor = sharedPreferences.edit()

        binding.fontSizeTv.text = "${getText(R.string.font_settings_font_size_tv_txt)} " +
                sharedPreferences.getInt("lesson_text_size", 16)
        binding.fontSizeSlider.value = sharedPreferences.getInt("lesson_text_size", 16).toFloat()

        binding.lineSpacingTv.text = "${getText(R.string.font_settings_line_spacing_tv_txt)} " +
                sharedPreferences.getFloat("lesson_text_line_spacing", 1.2f)
        binding.lineSpacingSlider.value =
            sharedPreferences.getFloat("lesson_text_line_spacing", 1.2f)

        binding.textAlignmentSwitch.isChecked =
            sharedPreferences.getBoolean("lesson_text_justification", false)

        setBorder(binding.sampleTextCv)
        setTextSettings(binding.cvText)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fontSizeSlider.addOnChangeListener(Slider.OnChangeListener { _: Slider?,
                                                                             value: Float,
                                                                             fromUser: Boolean ->
            if (fromUser) {
                binding.fontSizeTv.text = "${getText(R.string.font_settings_font_size_tv_txt)} $value"
                binding.cvText.textSize = value
            }
        })

        binding.lineSpacingSlider.addOnChangeListener(Slider.OnChangeListener { _: Slider?,
                                                                                value: Float,
                                                                                fromUser: Boolean ->
            if (fromUser) {
                binding.lineSpacingTv.text = "${getText(R.string.font_settings_line_spacing_tv_txt)} $value"
                binding.cvText.setLineSpacing(0f, value)
            }
        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            binding.textAlignmentSwitch.setOnCheckedChangeListener { _: CompoundButton?,
                                                                     isChecked: Boolean ->
                binding.cvText.justificationMode = booleanToJustificationMode(isChecked) }

        binding.saveBtn.setOnClickListener {
            editor.putInt("lesson_text_size", binding.fontSizeSlider.value.toInt())
            editor.putFloat("lesson_text_line_spacing", binding.lineSpacingSlider.value)
            editor.putBoolean("lesson_text_justification", binding.textAlignmentSwitch.isChecked)
            editor.apply()
        }
    }
}