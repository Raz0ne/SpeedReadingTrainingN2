package com.raz0ne.speedreading_training.data.models;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Lesson {
    private String textName, textAuthor, text, size;
    private int ageMin;
    private List<LessonQuestion> questions;

    public Lesson(String textName, String textAuthor, String text, String size,
                  int ageMin, @Nullable List<LessonQuestion> questions) {
        this.textName = textName;
        this.textAuthor = textAuthor;
        this.text = text;
        this.size = size;
        this.ageMin = ageMin;
        if (questions != null)
            this.questions = questions;
        else
            this.questions = new ArrayList<>();
    }

    public List<LessonQuestion> getQuestions() {
        return questions;
    }

    public String getTextName() {
        return textName;
    }

    public String getTextAuthor() {
        return textAuthor;
    }

    public String getText() {
        return text;
    }

    public String getSize() {
        return size;
    }

    public int getAgeMin() {
        return ageMin;
    }
}
