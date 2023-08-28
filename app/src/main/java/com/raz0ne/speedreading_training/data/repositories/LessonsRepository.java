package com.raz0ne.speedreading_training.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import com.raz0ne.speedreading_training.data.data_sources.LessonsDataSource;
import com.raz0ne.speedreading_training.data.data_sources.mappers.LessonMapper;
import com.raz0ne.speedreading_training.data.data_sources.room.root.AppDatabase;
import com.raz0ne.speedreading_training.data.models.Lesson;

public class LessonsRepository {
    private LessonsDataSource mDataSource;
    private AppDatabase databaseSource;

    public LessonsRepository(Application application) {
        this.mDataSource = new LessonsDataSource();
        this.databaseSource = AppDatabase.getDatabase(application);
    }

    public LiveData<List<Lesson>> getData() {
        return mDataSource.lessons();
    }
    public LiveData<List<Lesson>> getDatabaseData() {
        return Transformations.map(
                databaseSource.lessonDAO().getAllItems(),
                values -> values.stream().map(LessonMapper::getLesson)
                        .collect(Collectors.toList()));
    }

    public void addItem(Lesson newItem) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                databaseSource.lessonDAO().addNewItem(LessonMapper.getLessonEntity(newItem)));
    }

    public void deleteItem(Lesson item) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                databaseSource.lessonDAO().deleteItem(LessonMapper.getLessonEntity(item)));
    }
}
