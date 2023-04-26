package edu.application.ui.fragments.recycler_view_fragments;

import android.os.Bundle;
import android.util.Log;
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

import java.util.Objects;

import edu.application.R;
import edu.application.data.models.Lesson;
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

        Log.d("razon", "1");
        mViewModel.addItem(new Lesson("Почему плачет синичка?",
                "Сухомлинский А.В.",
                "   В доме на краю села жили муж и жена. Было у них двое детей - мальчик " +
                        "Миша и девочка Оля. Мише было десять лет, а Оле - девять. Около дома " +
                        "рос высокий ветвистый тополь.\n" +
                        "-Сделаем на тополе качели, - сказал Миша.\n" +
                        "-Ой, как хорошо будет качаться! - обрадовалась Оля.\n" +
                        "    Полез Миша на тополь, привязал к веткам верёвку. Встали на качели " +
                        "Миша и Оля, и давай качаться. Качаются дети, а около них синичка летает " +
                        "и поёт, поёт.\n" +
                        "   Миша говорит:" +
                        "-Синичке тоже весело оттого, что мы качаемся.\n" +
                        "   Глянула Оля на ствол дерева и увидела дупло, а в дупле гнёздышко, а в" +
                        "гнёздышке птички маленькие.\n" +
                        "-Синичка не радуется, а плачет, - сказала Оля.\n" +
                        "-А почему же она плачет? - удивился Миша.\n" +
                        "-Подумай, почему, - ответила Оля.\n" +
                        "   Миша спрыгнул с качелей, посмотрел на синичкино гнёздышко и думает: " +
                        "«Почему же она плачет?»",
                "очень маленький", 4, 6, null));
        mViewModel.addItem(new Lesson("Осень", "Осеева В.А.",
                "", "очень маленький", 4, 6, null));
    }
}
