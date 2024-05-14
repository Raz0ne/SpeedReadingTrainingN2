package com.raz0ne.speedreading_training.data.models

class Lesson(
    val textName: String,
    val textAuthor: String,
    val text: String,
    val size: String,
    val ageMin: Int, questions: List<LessonQuestion>?
) {
    var questions: List<LessonQuestion>? = null

    init {
        if (questions != null)
            this.questions = questions
        else
            this.questions = ArrayList()
    }
}
