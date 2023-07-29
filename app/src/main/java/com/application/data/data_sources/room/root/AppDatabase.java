package com.application.data.data_sources.room.root;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.application.data.data_sources.mappers.LessonMapper;
import com.application.data.data_sources.room.dao.LessonDAO;
import com.application.data.data_sources.room.entites.LessonEntity;
import com.application.data.models.Lesson;
import com.application.data.models.LessonQuestion;

@Database(entities = {LessonEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LessonDAO lessonDAO();

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "Lessons.db")
                            .addCallback(new Callback() {
                                public void onCreate (SupportSQLiteDatabase db) {
                                    String tab = "    ";
                                    ArrayList<LessonQuestion> lessonQuestions = new ArrayList<>();
                                    Lesson lesson;
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
                                    answers = Arrays.asList("А) большой", "Б) старый",
                                            "В) высокий ветвистый", "Г) высокий могучий");
                                    lessonQuestion = new LessonQuestion("4. Какой это был тополь?", answers,
                                            3);
                                    lessonQuestions.add(lessonQuestion);
                                    answers = Arrays.asList("А) домик на дереве",
                                            "Б) тайник на дереве", "В) качели");
                                    lessonQuestion = new LessonQuestion("5. Что предложил Миша сделать на тополе?", answers,
                                            3);
                                    lessonQuestions.add(lessonQuestion);
                                    lesson = new Lesson("Почему плачет синичка?",
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
                                            "очень маленький", 4,
                                            new ArrayList<>(lessonQuestions));

                                    Lesson finalLesson1 = lesson;
                                    Executors.newSingleThreadScheduledExecutor().execute(() ->
                                            getDatabase(context).lessonDAO().addNewItem(
                                                    LessonMapper.getLessonEntity(finalLesson1)));

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
                                    answers = Arrays.asList("А) холодные", "Б) тёплые и слабые",
                                            "В) холодные пронизывающие");
                                    lessonQuestion = new LessonQuestion("4. Какие ветры дуют осенью?", answers,
                                            3);
                                    lessonQuestions.add(lessonQuestion);
                                    answers = Arrays.asList("А) золотистые и красивые",
                                            "Б) стоят голые", "В) склоняются под сильным ветром");
                                    lessonQuestion = new LessonQuestion("5. Что говорится в рассказе про деревья?", answers,
                                            2);
                                    lessonQuestions.add(lessonQuestion);
                                    lesson = new Lesson("Осень",
                                            "Осеева В.А.",
                                            tab + "Осенью небо пасмурное, затянуто жёлтыми тучами. Солнце почти не " +
                                                    "выглядывает из-за туч. Дуют холодные пронизывающие ветры. Деревья и " +
                                                    "стоят голые. Облетел их зелёный наряд. Трава пожелтела и засохла. " +
                                                    "Кругом лужи и грязь.",
                                            "очень маленький", 5,
                                            new ArrayList<>(lessonQuestions));
                                    Lesson finalLesson2 = lesson;
                                    Executors.newSingleThreadScheduledExecutor().execute(() ->
                                            getDatabase(context).lessonDAO().addNewItem(
                                                    LessonMapper.getLessonEntity(finalLesson2)));

                                    lessonQuestions.clear();
                                    answers = Arrays.asList("А) осенью", "Б) летом", "В) весной");
                                    lessonQuestion = new LessonQuestion("1. В какое время года происходят события в рассказе?",
                                            answers, 3);
                                    lessonQuestions.add(lessonQuestion);
                                    answers = Arrays.asList("А) на острове", "Б) в лесу",
                                            "В) на островке среди широкой реки");
                                    lessonQuestion = new LessonQuestion("2. Где жил заяц?", answers, 3);
                                    lessonQuestions.add(lessonQuestion);
                                    answers = Arrays.asList("А) наводнение",
                                            "Б) на него напал волк", "В) он утонул");
                                    lessonQuestion = new LessonQuestion("3. Что произошло, пока заяц спал?", answers,
                                            1);
                                    lessonQuestions.add(lessonQuestion);
                                    answers = Arrays.asList("А) позволил людям его спасти",
                                            "Б) забрался на самый высокий холм",
                                            "В) вскочил на толстый нижний сук дерева");
                                    lessonQuestion = new LessonQuestion("4. Что сделал заяц, чтобы спастись?", answers,
                                            3);
                                    lessonQuestions.add(lessonQuestion);
                                    answers = Arrays.asList("А) его спасли люди",
                                            "Б) вода спала и он спрыгнул на землю",
                                            "В) заяц умер от голода");
                                    lessonQuestion = new LessonQuestion("5. Что произошло с зайцем через 3 дня после наводнения?", answers,
                                            2);
                                    lessonQuestions.add(lessonQuestion);
                                    lesson = new Lesson("Наводнение в лесу",
                                            "Бианки В.В.",
                                            tab + "С зайцем случилось вот что.\n" +
                                                    tab + "Заяц жил на островке среди широкой реки.\n" +
                                                    tab + "Река кругом его островка с треском сбрасывала лёд.\n" +
                                                    tab + "В тот день он спокойно спал у себя под кустом. " +
                                                    "Солнце пригревало его, и косой не заметил, " +
                                                    "как вода в реке стала быстро прибывать. " +
                                                    "Он проснулся только тогда, когда почувствовал, " +
                                                    "что шкурка его промокла снизу.\n" +
                                                    tab + "Вскочил — а вокруг него уже вода.\n" +
                                                    tab + "Началось наводнение. " +
                                                    "Заяц убежал на середину островка: " +
                                                    "там было ещё сухо.\n" +
                                                    tab + "Но вода в реке прибывала быстро. " +
                                                    "Островок становился всё меньше и меньше. " +
                                                    "Заяц метался с одного конца на другой.\n" +
                                                    tab + "Так прошли весь день и вся ночь.\n" +
                                                    tab + "На следующее утро из воды торчал только крошечный кусочек островка. " +
                                                    "На нём стояло сухое дерево. " +
                                                    "Заяц бегал кругом его толстого ствола.\n" +
                                                    tab + "А на третий день вода поднялась уже до самого дерева. " +
                                                    "Заяц стал прыгать на дерево, " +
                                                    "но каждый раз обрывался и шлёпался в воду.\n" +
                                                    tab + "Наконец ему удалось вскочить на толстый нижний сук. " +
                                                    "Заяц примостился на нём и стал терпеливо дожидаться конца наводнения: " +
                                                    "вода в реке больше уже не прибывала.\n" +
                                                    tab + "С голоду помереть он нс боялся: " +
                                                    "кора старого дерева была хоть очень жёсткая и горькая, " +
                                                    "но всё-таки в пищу ему годилась.\n" +
                                                    tab + "Ветер качал дерево так сильно, " +
                                                    "что заяц еле держался на суку. " +
                                                    "По широкой реке под ним плыли деревья, " +
                                                    "брёвна, сучья, солома.\n" +
                                                    tab + "Три дня просидел заяц на дереве. " +
                                                    "Наконец, вода спала, и он спрыгнул на землю.",
                                            "маленький", 6,
                                            new ArrayList<>(lessonQuestions));

                                    Lesson finalLesson3 = lesson;
                                    Executors.newSingleThreadScheduledExecutor().execute(() ->
                                            getDatabase(context).lessonDAO().addNewItem(
                                                    LessonMapper.getLessonEntity(finalLesson3)));
                                }
                            }).build();
                }
            }
        }
        return INSTANCE;
    }
}
