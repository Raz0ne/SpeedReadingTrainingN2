package com.raz0ne.speedreading_training.ui.screens.main.training.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.raz0ne.speedreading_training.data.models.Lesson
import com.raz0ne.speedreading_training.data.repositories.LessonsRepository

class LessonsViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: LessonsRepository = LessonsRepository(application)
    val items: LiveData<List<Lesson>> = repo.databaseData

    fun addItem(lesson: Lesson) {
        repo.addItem(lesson)
    }
}