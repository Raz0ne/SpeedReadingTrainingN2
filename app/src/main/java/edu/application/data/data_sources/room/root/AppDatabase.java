package edu.application.data.data_sources.room.root;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.application.R;
import edu.application.data.data_sources.mappers.LessonMapper;
import edu.application.data.data_sources.room.dao.LessonDAO;
import edu.application.data.data_sources.room.entites.LessonEntity;
import edu.application.data.models.Lesson;
import edu.application.data.models.LessonQuestion;

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

                                    Lesson finalLesson = lesson;
                                    Executors.newSingleThreadScheduledExecutor().execute(() ->
                                            getDatabase(context).lessonDAO().addNewItem(
                                                    LessonMapper.getLessonEntity(finalLesson)));

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
                                    lesson = new Lesson("Осень",
                                            "Осеева В.А.",
                                            tab + "Осенью небо пасмурное, затянуто жёлтыми тучами. Солнце почти не " +
                                                    "выглядывает из-за туч. Дуют холодные пронизывающие ветры. Деревья и " +
                                                    "стоят голые. Облетел их зелёный наряд. Трава пожелтела и засохла. " +
                                                    "Кругом лужи и грязь.",
                                            "очень маленький", 5,
                                            new ArrayList<>(lessonQuestions));

                                    Lesson finalLesson1 = lesson;
                                    Executors.newSingleThreadScheduledExecutor().execute(() ->
                                            getDatabase(context).lessonDAO().addNewItem(
                                                    LessonMapper.getLessonEntity(finalLesson1)));
                                }
                            }).build();
                }
            }
        }
        return INSTANCE;
    }
}
