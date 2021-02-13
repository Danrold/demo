package com.example.demo.service;

import com.example.demo.entity.Survey;

import javax.persistence.EntityNotFoundException;

/**
 * Интерфейс описывающй методы для работы с вопросами
 */

public interface QuestionService {

    Survey addQuestion(String surveyID, String text) throws EntityNotFoundException;

    Survey editQuestion(String questionID, String text) throws EntityNotFoundException;

    Survey deleteQuestion(String questionID) throws EntityNotFoundException;
}
