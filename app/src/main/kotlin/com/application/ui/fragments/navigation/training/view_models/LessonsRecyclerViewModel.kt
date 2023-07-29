package com.application.ui.fragments.navigation.training.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.application.data.models.Lesson
import com.application.data.repositories.LessonsRepository

class LessonsRecyclerViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: LessonsRepository = LessonsRepository(application)
    val items: LiveData<List<Lesson>> = repo.databaseData

    fun addItem(lesson: Lesson) {
        repo.addItem(lesson)
    }
}