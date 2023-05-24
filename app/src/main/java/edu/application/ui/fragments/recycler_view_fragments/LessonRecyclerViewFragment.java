package edu.application.ui.fragments.recycler_view_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import edu.application.R;
import edu.application.data.models.Lesson;
import edu.application.data.models.LessonQuestion;
import edu.application.databinding.LessonsRecyclerViewFragmentBinding;
import edu.application.ui.adapters.LessonsRecyclerViewAdapter;
import edu.application.ui.view_models.LessonsRecyclerViewModel;

public class LessonRecyclerViewFragment extends Fragment {
    private LessonsRecyclerViewFragmentBinding mBinding;
    private LessonsRecyclerViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = LessonsRecyclerViewFragmentBinding.inflate(inflater, container,
                false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(LessonsRecyclerViewModel.class);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration itemDecorator =
                new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(
                ContextCompat.getDrawable(requireContext(), R.drawable.divider)));
        mBinding.recyclerView.addItemDecoration(itemDecorator);
        mBinding.recyclerView.setAdapter(new LessonsRecyclerViewAdapter());

        mViewModel.getItems().observe(getViewLifecycleOwner(), (value) ->
                ((LessonsRecyclerViewAdapter) Objects.requireNonNull(
                        mBinding.recyclerView.getAdapter()))
                        .updateData(value));
    }
}

