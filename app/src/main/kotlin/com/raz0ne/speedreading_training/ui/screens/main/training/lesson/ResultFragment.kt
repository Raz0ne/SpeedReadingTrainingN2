package com.raz0ne.speedreading_training.ui.screens.main.training.lesson

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.raz0ne.speedreading_training.R
import com.raz0ne.speedreading_training.databinding.FragmentLessonReadingResultBinding

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentLessonReadingResultBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLessonReadingResultBinding
            .inflate(inflater, container, false)

        binding.scoreTv.append(" ${arguments!!.getInt("correct_answers")}/" +
                arguments!!.getInt("questions_cnt"))

        binding.menuBtn.setOnClickListener {
            findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_lessonReadingResultFragment_to_trainingFragment) }

        return binding.root
    }
}