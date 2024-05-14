package com.raz0ne.speedreading_training.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raz0ne.speedreading_training.data.data_sources.LessonsDataSource
import com.raz0ne.speedreading_training.data.data_sources.mappers.getLessonEntity
import com.raz0ne.speedreading_training.data.data_sources.room.dao.LessonsDAO
import com.raz0ne.speedreading_training.data.data_sources.room.root.LessonsRoomDatabase
import com.raz0ne.speedreading_training.data.models.Lesson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.stream.Collectors

class LessonsRepository(private val lessonsDao: LessonsDAO) {

    val searchResults = MutableLiveData<List<Lesson>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val data: LiveData<List<Lesson>>
        get() = mDataSource.lessons()

    val databaseData: LiveData<List<Lesson>>
        get() = databaseSource.lessonsDAO().allItems.map<Any, List<Lesson>> { values: Any ->
            values.stream().map(getLesson)
                .collect(Collectors.toList())
        }

    fun addItem(newItem: Lesson) {
        coroutineScope.launch(Dispatchers.IO) {
            lessonsDao.addNewItem(getLessonEntity(newItem))
        }
    }

    fun deleteItem(item: Lesson) {
        coroutineScope.launch(Dispatchers.IO) {
            lessonsDao.deleteItem(getLessonEntity(item))
        }
    }

    fun findItem(item: Lesson) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(item).await()
        }
    }

    private fun asyncFind(item: Lesson): Deferred<List<Lesson>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async lessonsDao.addNewItem(item)
        }
}