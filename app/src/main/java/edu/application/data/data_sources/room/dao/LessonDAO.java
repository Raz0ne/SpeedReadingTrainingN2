package edu.application.data.data_sources.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.application.data.data_sources.room.entites.LessonEntity;

@Dao
public interface LessonDAO {
    @Query("SELECT * FROM LessonEntity")
    LiveData<List<LessonEntity>> getAllItems();

    @Insert
    void addNewItem(LessonEntity newItem);

    @Delete
    void deleteItem(LessonEntity item);
}
