package edu.application.data.models;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Lesson {
    private String textName, textAuthor, text, size;
    private int ageMin, ageMax;
    private List<LessonQuestion> questions;

    public Lesson(String textName, String textAuthor, String text, String size,
                  int ageMin, int ageMax, @Nullable List<LessonQuestion> questions) {
        this.textName = textName;
        this.textAuthor = textAuthor;
        this.text = text;
        this.size = size;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
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

    public int getAgeMax() {
        return ageMax;
    }
}
