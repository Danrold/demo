package com.example.demo.service;

import com.example.demo.entity.Survey;

import javax.persistence.EntityNotFoundException;

/**
 * Интерфейс описывающй методы для работы с вопросами
 * @author Ivan Medvedev
 */

public interface QuestionService {

    /**
     * Метод добавляет вопрос к опросу по указанному идентификатору
     * @param surveyID Идентификатор опроса
     * @param text Текст вопроса
     * @return Обновленный опрос
     * @throws EntityNotFoundException Бросается если опрос не был найден
     */
    Survey addQuestion(String surveyID, String text) throws EntityNotFoundException;

    /**
     * Метод изменяет текст вопроса
     * @param questionID Идентификатор вопроса
     * @param text Новый текст вопроса
     * @return Обновленный опрос
     * @throws EntityNotFoundException Бросается если вопрос не был найден
     */
    Survey editQuestion(String questionID, String text) throws EntityNotFoundException;

    /**
     * Метод удаляет вопрос по идентификатору
     * @param questionID Идентификатор вопроса
     * @return Обновленный опрос
     * @throws EntityNotFoundException Бросается если вопрос не был найден
     */
    Survey deleteQuestion(String questionID) throws EntityNotFoundException;
}
