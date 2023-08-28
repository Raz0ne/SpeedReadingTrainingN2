package com.raz0ne.speedreading_training.ui.fragments.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.raz0ne.speedreading_training.databinding.FragmentTrainingBinding

class TrainingFragment : Fragment() {

    private lateinit var binding: FragmentTrainingBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentTrainingBinding.inflate(inflater, container, false)

        return binding.root
    }
}