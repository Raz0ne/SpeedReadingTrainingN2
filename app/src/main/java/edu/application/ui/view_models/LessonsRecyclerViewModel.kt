package edu.application.ui.view_models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.application.data.models.Lesson;
import edu.application.data.repositories.LessonsRepository;

public class LessonsRecyclerViewModel  extends AndroidViewModel {
    private LessonsRepository repo;

    private LiveData<List<Lesson>> mItems;

    public LessonsRecyclerViewModel(Application application) {
        super(application);
        this.repo = new LessonsRepository(application);
        mItems = repo.getDatabaseData();
    }

    public LiveData<List<Lesson>> getItems() {
        return mItems;
    }

    public void addItem(Lesson lesson) {
        repo.addItem(lesson);
    }
}