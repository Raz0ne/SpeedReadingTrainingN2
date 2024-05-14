package com.raz0ne.speedreading_training.ui.screens.main

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raz0ne.speedreading_training.data.models.Lesson
import com.raz0ne.speedreading_training.databinding.FragmentTrainingBinding
import com.raz0ne.speedreading_training.ui.screens.main.training.LessonsList
import com.raz0ne.speedreading_training.ui.screens.main.training.view_models.LessonsViewModel
import com.raz0ne.speedreading_training.ui.theme.AppTheme

/*
class TrainingScreen : Fragment() {

    private lateinit var binding: FragmentTrainingBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentTrainingBinding.inflate(inflater, container, false)

        return binding.root
    }
}*/


@Composable
fun TrainingScreen() {
    val viewModel = ViewModelProvider(LocalContext.current as ViewModelStoreOwner)[LessonsViewModel::class.java]
        //viewModel(factory = MyViewModelFactory("db2name"))

    LessonsList(viewModel)
}

@Preview
@Composable
fun TrainingScreenPreview() {
    AppTheme {
        TrainingScreen()
    }
}
