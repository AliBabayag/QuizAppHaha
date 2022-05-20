package com.example.demo1.model;


import java.util.List;


public class Quiz {

    private List<QuestionModel.Question> questions;

    public Quiz() {
        super();
    }

    public Quiz(List<QuestionModel.Question> questions) {
        this.questions = questions;
    }

    public List<QuestionModel.Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionModel.Question> questions) {
        this.questions = questions;
    }
}
