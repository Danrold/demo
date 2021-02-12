package com.example.demo.service;

import com.example.demo.entity.Survey;

/**
 * Интерфейс описывающй методы для работы с вопросами
 */

public interface QuestionService {

    Survey addQuestion(String surveyID, String text);

    Survey editQuestion(String questionID, String text);

    Survey deleteQuestion(String questionID);
}
