package edu.application.data.data_sources.room.entites;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.application.data.models.Lesson;

@Entity
public class LessonEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public String textName, textAuthor, text, size;

    @ColumnInfo
    public int ageMin, ageMax;

    @ColumnInfo
    public String questions;

    @ColumnInfo
    public String answers;

    public LessonEntity(String textName, String textAuthor, String text, String size,
                        int ageMin, int ageMax, String questions, String answers) {
        this.textName = textName;
        this.textAuthor = textAuthor;
        this.text = text;
        this.size = size;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.questions = questions;
        this.answers = answers;
    }
}
