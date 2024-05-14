package com.raz0ne.speedreading_training.data.data_sources.mappers

import kotlinx.serialization.json.*
import com.raz0ne.speedreading_training.data.data_sources.room.entites.LessonEntity
import com.raz0ne.speedreading_training.data.models.Lesson
import com.raz0ne.speedreading_training.data.models.LessonQuestion
import kotlinx.serialization.encodeToString

fun getLesson(lessonEntity: LessonEntity): Lesson {
    return if (lessonEntity.questions == "") Lesson(
        lessonEntity.textName, lessonEntity.textAuthor, lessonEntity.text, lessonEntity.size,
        lessonEntity.ageMin, null
    ) else Lesson(
        lessonEntity.textName, lessonEntity.textAuthor, lessonEntity.text, lessonEntity.size,
        lessonEntity.ageMin, toQuestionListFromJson(lessonEntity.questions)
    )
}

fun getLessonEntity(lessonModel: Lesson): LessonEntity {
    return if (lessonModel.questions.isEmpty())
        LessonEntity(lessonModel.textName, lessonModel.textAuthor, lessonModel.text,
            lessonModel.size, lessonModel.ageMin, ""
    ) else LessonEntity(
        lessonModel.textName, lessonModel.textAuthor, lessonModel.text, lessonModel.size,
        lessonModel.ageMin, fromQuestionListToJson(lessonModel.questions)
    )
}

private fun fromQuestionListToJson(value: List<LessonQuestion>): String {
    return Json.encodeToString(value)
}

private fun toQuestionListFromJson(value: String): List<LessonQuestion> {
    return Json.decodeFromString(value)
}