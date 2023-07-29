package com.application.ui.fragments.navigation.training.lesson

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.application.R
import com.application.databinding.FragmentLessonPreparingBinding

class PreparingFragment : Fragment() {

    private lateinit var handler: Handler
    private lateinit var binding: FragmentLessonPreparingBinding

    private inner class Task(var number: Int) : Runnable {

        override fun run() {
            if (activity == null)
                return

            if (number > 0) {
                binding.digit.text = number.toString()
                handler.postDelayed(Task(number - 1), 1000)
            }
            else if (number == 0) {
                binding.digit.text = "0"
                handler.postDelayed(Task(number - 1), 500)
            }
            else
                findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.action_preparingFragment_to_lessonReadingTextFragment, arguments)
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLessonPreparingBinding.inflate(inflater, container, false)

        handler = Handler(Looper.getMainLooper())

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        handler.post(Task(3))
    }
}