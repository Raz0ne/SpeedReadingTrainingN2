package com.application.ui.fragments.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.application.databinding.FragmentNavigationTrainingBinding

class TrainingFragment : Fragment() {

    private lateinit var binding: FragmentNavigationTrainingBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentNavigationTrainingBinding.inflate(inflater, container, false)

        return binding.root
    }
}