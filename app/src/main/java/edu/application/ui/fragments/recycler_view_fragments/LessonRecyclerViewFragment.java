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

        String tab = getString(R.string.tab);

        ArrayList<LessonQuestion> lessonQuestions = new ArrayList<>();
        List<String> answers = Arrays.asList("А) муж и жена", "Б) мальчик и девочка",
                "В) муж, жена и их двое детей");
        LessonQuestion lessonQuestion = new LessonQuestion(
                "1. Кто жил в доме на краю села?", answers, 3);
        lessonQuestions.add(lessonQuestion);
        answers = Arrays.asList("А) они одинакового возраста", "Б) Оле - 9 лет, Мише - 10",
                "В) девочке - 10 лет, мальчику - 9");
        lessonQuestion = new LessonQuestion("2. Сколько лет было детям?", answers,
                2);
        lessonQuestions.add(lessonQuestion);
        answers = Arrays.asList("А) в лесу", "Б) около дома", "В) на улице");
        lessonQuestion = new LessonQuestion("3. Где рос тополь?", answers, 2);
        lessonQuestions.add(lessonQuestion);
        mViewModel.addItem(new Lesson("Почему плачет синичка?",
                "Сухомлинский А.В.",
                tab + "В доме на краю села жили муж и жена. Было у них двое детей - мальчик " +
                        "Миша и девочка Оля. Мише было десять лет, а Оле - девять. Около дома " +
                        "рос высокий ветвистый тополь.\n" +
                        "- Сделаем на тополе качели, - сказал Миша.\n" +
                        "- Ой, как хорошо будет качаться! - обрадовалась Оля.\n" +
                        tab + "Полез Миша на тополь, привязал к веткам верёвку. Встали на качели " +
                        "Миша и Оля, и давай качаться. Качаются дети, а около них синичка летает " +
                        "и поёт, поёт.\n" +
                        tab + "Миша говорит:\n" +
                        "- Синичке тоже весело оттого, что мы качаемся.\n" +
                        tab + "Глянула Оля на ствол дерева и увидела дупло, а в дупле гнёздышко, а " +
                        "в гнёздышке птички маленькие.\n" +
                        "- Синичка не радуется, а плачет, - сказала Оля.\n" +
                        "- А почему же она плачет? - удивился Миша.\n" +
                        "- Подумай, почему, - ответила Оля.\n" +
                        tab + "Миша спрыгнул с качелей, посмотрел на синичкино гнёздышко и " +
                        "думает: «Почему же она плачет?»",
                "очень маленький", 4, new ArrayList<>(lessonQuestions)));

        lessonQuestions.clear();
        answers = Arrays.asList("А) о весне", "Б) о лете", "В) об осени", "Г) о зиме");
        lessonQuestion = new LessonQuestion("1. О каком времени года говорится в рассказе?",
                answers, 3);
        lessonQuestions.add(lessonQuestion);
        answers = Arrays.asList("А) серое", "Б) затянуто тучами", "В) пасмурное",
                "Г) пасмурное, затянуто тяжёлыми тучами");
        lessonQuestion = new LessonQuestion("2. Какое осенью небо?", answers, 4);
        lessonQuestions.add(lessonQuestion);
        answers = Arrays.asList("А) тусклое и холодное", "Б) почти не выглядывает из-за туч",
                "В) грустное");
        lessonQuestion = new LessonQuestion("3. Что сказано про солнце осенью?", answers,
                2);
        lessonQuestions.add(lessonQuestion);
        mViewModel.addItem(new Lesson("Осень", "Осеева В.А.",
                tab + "Осенью небо пасмурное, затянуто жёлтыми тучами. Солнце почти не " +
                        "выглядывает из-за туч. Дуют холодные пронизывающие ветры. Деревья и " +
                        "стоят голые. Облетел их зелёный наряд. Трава пожелтела и засохла. " +
                        "Кругом лужи и грязь.",
                "очень маленький", 5, new ArrayList<>(lessonQuestions)));
    }
}

