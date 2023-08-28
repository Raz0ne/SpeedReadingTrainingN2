package com.raz0ne.speedreading_training.ui.fragments.screens.training.lesson

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.method.ScrollingMovementMethod
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.raz0ne.speedreading_training.R
import com.raz0ne.speedreading_training.databinding.FragmentLessonReadingTextBinding
import com.raz0ne.speedreading_training.ui.fragments.screens.adapters.TextFormatter.setTextSettings
import kotlin.math.abs

class LessonReadingTextFragment : Fragment() {

    private lateinit var binding: FragmentLessonReadingTextBinding
    private lateinit var colors: ArrayList<Int>
    private lateinit var wordsIndexes: ArrayList<Int>
    private lateinit var textSpannable: Spannable
    private lateinit var handler: Handler

    private inner class Task(var idx: Int) : Runnable {

        override fun run() {
            if (activity == null)
                return

            for (d in -4..4) {
                try {
                    textSpannable.setSpan(ForegroundColorSpan(colors[abs(d)]),
                        wordsIndexes[idx + d], wordsIndexes[idx + d + 1],
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                } catch (ignored: IndexOutOfBoundsException) {}
            }
            binding.text.text = textSpannable

            if (idx < wordsIndexes.size - 1)
                handler.postDelayed(Task(idx + 1), 100)
            else
                findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(
                        R.id.action_lessonReadingTextFragment_to_lessonReadingQuestionsFragment,
                        arguments)
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLessonReadingTextBinding.inflate(inflater, container, false)
        binding.text.text = arguments!!.getString("text")
        setTextSettings(binding.text)
        binding.text.movementMethod = ScrollingMovementMethod()

        val textColor = binding.text.currentTextColor
        val backgroundColor = (binding.text.background as ColorDrawable).color
        binding.root.setBackgroundColor(backgroundColor)

        colors = ArrayList()
        colors.add(textColor)
        colors.add(ColorUtils.blendARGB(textColor, backgroundColor, 0.15f))
        colors.add(ColorUtils.blendARGB(textColor, backgroundColor, 0.3f))
        colors.add(ColorUtils.blendARGB(textColor, backgroundColor, 0.45f))
        colors.add(ColorUtils.blendARGB(textColor, backgroundColor, 0.5f))

        binding.text.setTextColor(colors[4])

        val text = arguments!!.getString("text")!!
        textSpannable = SpannableString(text)

        wordsIndexes = ArrayList()
        wordsIndexes.add(0)
        var index = text.indexOf(' ')
        while (index >= 0) {
            wordsIndexes.add(index + 1)
            index = text.indexOf(' ', index + 1)
        }
        wordsIndexes.add(text.length - 1)

        handler = Handler(Looper.getMainLooper())

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        handler.post(Task(0))
    }
}