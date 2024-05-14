package com.raz0ne.speedreading_training.data.data_sources.room.root

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.raz0ne.speedreading_training.data.data_sources.mappers.getLessonEntity
import com.raz0ne.speedreading_training.data.data_sources.room.dao.LessonsDAO
import com.raz0ne.speedreading_training.data.data_sources.room.entites.LessonEntity
import com.raz0ne.speedreading_training.data.models.Lesson
import com.raz0ne.speedreading_training.data.models.LessonQuestion
import java.util.concurrent.Executors

@Database(entities = [LessonEntity::class], version = 1)
abstract class LessonsRoomDatabase : RoomDatabase() {
    abstract fun lessonsDAO(): LessonsDAO

    companion object {

        private var INSTANCE: LessonsRoomDatabase? = null

        fun getInstance(context: Context): LessonsRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = databaseBuilder(
                        context.applicationContext,
                        LessonsRoomDatabase::class.java, "Lessons.db"
                    ).addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            val tab = "    "
                            val lessonQuestions = ArrayList<LessonQuestion>()
                            var answers = listOf(
                                "А) муж и жена", "Б) мальчик и девочка",
                                "В) муж, жена и их двое детей")
                            var lessonQuestion = LessonQuestion(
                                "1. Кто жил в доме на краю села?", answers, 3)
                            lessonQuestions.add(lessonQuestion)
                            answers = listOf(
                                "А) они одинакового возраста", "Б) Оле - 9 лет, Мише - 10",
                                "В) девочке - 10 лет, мальчику - 9")
                            lessonQuestion = LessonQuestion(
                                "2. Сколько лет было детям?", answers, 2)
                            lessonQuestions.add(lessonQuestion)
                            answers = listOf("А) в лесу", "Б) около дома", "В) на улице")
                            lessonQuestion = LessonQuestion(
                                "3. Где рос тополь?", answers, 2)
                            lessonQuestions.add(lessonQuestion)
                            answers = listOf(
                                "А) большой", "Б) старый", "В) высокий ветвистый",
                                "Г) высокий могучий")
                            lessonQuestion = LessonQuestion(
                                "4. Какой это был тополь?", answers, 3)
                            lessonQuestions.add(lessonQuestion)
                            answers = listOf(
                                "А) домик на дереве", "Б) тайник на дереве", "В) качели")
                            lessonQuestion = LessonQuestion(
                                "5. Что предложил Миша сделать на тополе?", answers,
                                3)
                            lessonQuestions.add(lessonQuestion)
                            var lesson = Lesson(
                                "Почему плачет синичка?", "Сухомлинский А.В.",
                                """${tab}В доме на краю села жили муж и жена. Было у них двое детей - мальчик Миша и девочка Оля. Мише было десять лет, а Оле - девять. Около дома рос высокий ветвистый тополь.
                                    - Сделаем на тополе качели, - сказал Миша.
                                    - Ой, как хорошо будет качаться! - обрадовалась Оля.
                                    ${tab}Полез Миша на тополь, привязал к веткам верёвку. Встали на качели Миша и Оля, и давай качаться. Качаются дети, а около них синичка летает и поёт, поёт.
                                    ${tab}Миша говорит:
                                    - Синичке тоже весело оттого, что мы качаемся.
                                    ${tab}Глянула Оля на ствол дерева и увидела дупло, а в дупле гнёздышко, а в гнёздышке птички маленькие.
                                    - Синичка не радуется, а плачет, - сказала Оля.
                                    - А почему же она плачет? - удивился Миша.
                                    - Подумай, почему, - ответила Оля.
                                    ${tab}Миша спрыгнул с качелей, посмотрел на синичкино гнёздышко и думает: «Почему же она плачет?»""",
                                "очень маленький", 4, lessonQuestions)
                            Executors.newSingleThreadScheduledExecutor().execute {
                                getInstance(context).lessonsDAO().addNewItem(
                                    getLessonEntity(lesson)
                                )
                            }

                            lessonQuestions.clear()
                            answers = listOf("А) о весне", "Б) о лете", "В) об осени", "Г) о зиме")
                            lessonQuestion = LessonQuestion(
                                "1. О каком времени года говорится в рассказе?", answers,
                                3)
                            lessonQuestions.add(lessonQuestion)
                            answers = listOf(
                                "А) серое", "Б) затянуто тучами", "В) пасмурное",
                                "Г) пасмурное, затянуто тяжёлыми тучами")
                            lessonQuestion = LessonQuestion(
                                "2. Какое осенью небо?", answers, 4)
                            lessonQuestions.add(lessonQuestion)
                            answers = listOf(
                                "А) тусклое и холодное", "Б) почти не выглядывает из-за туч",
                                "В) грустное")
                            lessonQuestion = LessonQuestion(
                                "3. Что сказано про солнце осенью?", answers,
                                2)
                            lessonQuestions.add(lessonQuestion)
                            answers = listOf(
                                "А) холодные", "Б) тёплые и слабые", "В) холодные пронизывающие")
                            lessonQuestion = LessonQuestion(
                                "4. Какие ветры дуют осенью?", answers, 3)
                            lessonQuestions.add(lessonQuestion)
                            answers = listOf(
                                "А) золотистые и красивые", "Б) стоят голые",
                                "В) склоняются под сильным ветром")
                            lessonQuestion = LessonQuestion(
                                "5. Что говорится в рассказе про деревья?", answers,
                                2)
                            lessonQuestions.add(lessonQuestion)
                            lesson = Lesson(
                                "Осень", "Осеева В.А.",
                                """${tab}Осенью небо пасмурное, затянуто жёлтыми тучами. Солнце почти не выглядывает из-за туч. Дуют холодные пронизывающие ветры. Деревья и стоят голые. Облетел их зелёный наряд. Трава пожелтела и засохла. Кругом лужи и грязь.""",
                                "очень маленький", 5, lessonQuestions)

                            Executors.newSingleThreadScheduledExecutor().execute {
                                getInstance(context).lessonsDAO().addNewItem(
                                    getLessonEntity(lesson)
                                )
                            }
                            lessonQuestions.clear()
                            answers = listOf("А) осенью", "Б) летом", "В) весной")
                            lessonQuestion = LessonQuestion(
                                "1. В какое время года происходят события в рассказе?",
                                answers, 3
                            )
                            lessonQuestions.add(lessonQuestion)
                            answers = listOf(
                                "А) на острове", "Б) в лесу", "В) на островке среди широкой реки")
                            lessonQuestion = LessonQuestion(
                                "2. Где жил заяц?", answers, 3)
                            lessonQuestions.add(lessonQuestion)
                            answers = listOf(
                                "А) наводнение", "Б) на него напал волк", "В) он утонул")
                            lessonQuestion = LessonQuestion(
                                "3. Что произошло, пока заяц спал?", answers,
                                1)
                            lessonQuestions.add(lessonQuestion)
                            answers = listOf(
                                "А) позволил людям его спасти", "Б) забрался на самый высокий холм",
                                "В) вскочил на толстый нижний сук дерева")
                            lessonQuestion = LessonQuestion(
                                "4. Что сделал заяц, чтобы спастись?", answers,
                                3)
                            lessonQuestions.add(lessonQuestion)
                            answers = listOf(
                                "А) его спасли люди", "Б) вода спала и он спрыгнул на землю",
                                "В) заяц умер от голода")
                            lessonQuestion = LessonQuestion(
                                "5. Что произошло с зайцем через 3 дня после наводнения?",
                                answers, 2)
                            lessonQuestions.add(lessonQuestion)
                            lesson = Lesson(
                                "Наводнение в лесу", "Бианки В.В.",
                                """${tab}С зайцем случилось вот что.
                                    ${tab}Заяц жил на островке среди широкой реки.
                                    ${tab}Река кругом его островка с треском сбрасывала лёд.
                                    ${tab}В тот день он спокойно спал у себя под кустом. Солнце пригревало его, и косой не заметил, как вода в реке стала быстро прибывать. Он проснулся только тогда, когда почувствовал, что шкурка его промокла снизу.
                                    ${tab}Вскочил — а вокруг него уже вода.
                                    ${tab}Началось наводнение. Заяц убежал на середину островка: там было ещё сухо.
                                    ${tab}Но вода в реке прибывала быстро. Островок становился всё меньше и меньше. Заяц метался с одного конца на другой.
                                    ${tab}Так прошли весь день и вся ночь.
                                    ${tab}На следующее утро из воды торчал только крошечный кусочек островка. На нём стояло сухое дерево. Заяц бегал кругом его толстого ствола.
                                    ${tab}А на третий день вода поднялась уже до самого дерева. Заяц стал прыгать на дерево, но каждый раз обрывался и шлёпался в воду.
                                    ${tab}Наконец ему удалось вскочить на толстый нижний сук. Заяц примостился на нём и стал терпеливо дожидаться конца наводнения: вода в реке больше уже не прибывала.
                                    ${tab}С голоду помереть он нс боялся: кора старого дерева была хоть очень жёсткая и горькая, но всё-таки в пищу ему годилась.
                                    ${tab}Ветер качал дерево так сильно, что заяц еле держался на суку. По широкой реке под ним плыли деревья, брёвна, сучья, солома.
                                    ${tab}Три дня просидел заяц на дереве. Наконец, вода спала, и он спрыгнул на землю.""".trimIndent(),
                                "маленький", 6, lessonQuestions)
                            Executors.newSingleThreadScheduledExecutor().execute {
                                getInstance(context).lessonsDAO().addNewItem(
                                    getLessonEntity(lesson)
                                )
                            }
                        }
                    }).build()
                }
                return instance
            }
        }
    }
}