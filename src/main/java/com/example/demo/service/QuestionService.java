package com.example.demo.service;

import com.example.demo.entity.Survey;

public interface QuestionService {

    Survey addQuestion(String surveyID, String text);

    Survey editQuestion(String questionID, String text);

    Survey deleteQuestion(String questionID);
}
