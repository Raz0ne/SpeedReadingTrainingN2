package edu.application.data.models;

public class LessonQuestion {

    private String question, answer;

    public LessonQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
