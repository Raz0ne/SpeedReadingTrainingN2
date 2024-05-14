package com.raz0ne.speedreading_training.data.data_sources.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.raz0ne.speedreading_training.data.data_sources.room.entites.LessonEntity

@Dao
interface LessonsDAO {

    @Query("SELECT * FROM lessons")
    fun getAllItems(): LiveData<List<LessonEntity>>

    @Insert
    fun addNewItem(newItem: LessonEntity)

    @Delete
    fun deleteItem(item: LessonEntity)
}