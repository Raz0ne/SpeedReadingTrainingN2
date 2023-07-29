package com.application.data.data_sources.mappers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import com.application.data.data_sources.room.entites.LessonEntity;
import com.application.data.models.Lesson;
import com.application.data.models.LessonQuestion;

public class LessonMapper {
    public static Lesson getLesson(LessonEntity lessonEntity) {
        if (Objects.equals(lessonEntity.questions, ""))
            return new Lesson(lessonEntity.textName, lessonEntity.textAuthor, lessonEntity.text,
                    lessonEntity.size, lessonEntity.ageMin, null);
        return new Lesson(lessonEntity.textName, lessonEntity.textAuthor, lessonEntity.text,
                lessonEntity.size, lessonEntity.ageMin,
                toQuestionListFromJson(lessonEntity.questions));
    }

    public static LessonEntity getLessonEntity(Lesson lessonModel) {
        if (lessonModel.getQuestions().isEmpty())
            return new LessonEntity(lessonModel.getTextName(), lessonModel.getTextAuthor(),
                    lessonModel.getText(), lessonModel.getSize(), lessonModel.getAgeMin(),
                    "");

        //Map<String, String> questionsMap = fromQuestionList(lessonModel.getQuestions());

        return new LessonEntity(lessonModel.getTextName(), lessonModel.getTextAuthor(),
                lessonModel.getText(), lessonModel.getSize(), lessonModel.getAgeMin(),
                fromQuestionListToJson(lessonModel.getQuestions()));
    }

    private static String fromQuestionListToJson(List<LessonQuestion> value) {
        Gson gson = new Gson();
        return gson.toJson(value);
    }

    private static List<LessonQuestion> toQuestionListFromJson(String value) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<LessonQuestion>>() {}.getType();
        return gson.fromJson(value, type);
    }

    /*private static Map<String, String> fromQuestionList(List<LessonQuestion> lessonQuestions) {
        Map<String, String> map = new HashMap<>();
        for (LessonQuestion question : lessonQuestions)
            map.put(question.getQuestion(), question.getAnswer());

        return map;
    }*/

    /*private static List<LessonQuestion> toQuestionList(List<String> questions,
                                                       List<String> answers,
                                                       int correctAnswer) {
        List<LessonQuestion> questionsList = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++)
            questionsList.add(new LessonQuestion(questions.get(i), answers.get(i), ));

        return questionsList;
    }*/
}
