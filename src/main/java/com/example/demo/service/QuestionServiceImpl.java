package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Имплементация интерфейса QuestionService
 * @author Ivan Medvedev
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    /**
     * JPA репозиторий для работы с сущностью Вопрос
     */
    private final QuestionRepository questionRepository;
    /**
     * JPA репозиторий для работы с сущностью Опрос
     */
    private final SurveyRepository surveyRepository;

    /**
     * Конструктор класса
     * @param questionRepository JPA репозиторий для работы с сущностью Вопрос
     * @param surveyRepository JPA репозиторий для работы с сущностью Опрос
     */
    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, SurveyRepository surveyRepository) {
        this.questionRepository = questionRepository;
        this.surveyRepository = surveyRepository;
    }

    /**
     * Метод добавляет вопрос к опросу по указанному идентификатору
     * @param surveyID Идентификатор опроса
     * @param text Текст вопроса
     * @return Обновленный опрос
     * @throws RuntimeException Бросается если опрос не был найден
     */
    @Override
    public Survey addQuestion(String surveyID, String text) throws RuntimeException{
        Survey survey = surveyRepository.findById(UUID.fromString(surveyID)).orElseThrow(()->new RuntimeException("Опрос с данным ID не найден"));
        List<Question> questions = survey.getQuestions();
        int questionCount = questions.size();
        Question question = new Question(text, ++questionCount, survey);
        questionRepository.saveAndFlush(question);
        questions.add(question);
        return survey;
    }

    /**
     * Метод изменяет текст вопроса
     * @param questionID Идентификатор вопроса
     * @param text Новый текст вопроса
     * @return Обновленный опрос
     * @throws RuntimeException Бросается если вопрос не был найден
     */
    @Override
    public Survey editQuestion(String questionID, String text) throws RuntimeException{
        Question question = questionRepository.findById(UUID.fromString(questionID)).orElseThrow(()->new RuntimeException("Вопрос с данным ID не найден"));
        question.setText(text);
        questionRepository.saveAndFlush(question);
        return question.getSurvey();
    }

    /**
     * Метод удаляет вопрос по идентификатору
     * @param questionID Идентификатор вопроса
     * @return Обновленный опрос
     * @throws RuntimeException Бросается если вопрос не был найден
     */
    @Override
    public Survey deleteQuestion(String questionID) throws RuntimeException{
        Question question = questionRepository.findById(UUID.fromString(questionID)).orElseThrow(()->new RuntimeException("Вопрос с данным ID не найден"));
        Survey survey = question.getSurvey();
        List<Question> questions = survey.getQuestions();
        int deleteIndex = question.getIndex();
        for (Question q: questions) {
            int currentIndex = q.getIndex();
            if(currentIndex>deleteIndex){
                q.setIndex(--currentIndex);
            }
        }
        questions.remove(question);
        survey.setQuestions(questions);
        questionRepository.delete(question);
        return survey;
    }
}
