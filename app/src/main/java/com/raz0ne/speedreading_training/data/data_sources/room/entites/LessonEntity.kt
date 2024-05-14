package com.raz0ne.speedreading_training.data.data_sources.room.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
class LessonEntity(

    @ColumnInfo
    var textName: String,

    @ColumnInfo
    var textAuthor: String,

    @ColumnInfo
    var text: String,

    @ColumnInfo
    var size: String,

    @ColumnInfo
    var ageMin: Int,

    @ColumnInfo
    var questions: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id = 0
}