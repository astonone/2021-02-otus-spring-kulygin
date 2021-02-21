package ru.otus.kulygin.domain;

public class QuestionAndAnswer {
    private final String question;
    private final String answer;

    public QuestionAndAnswer(String question, String answer) {
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
