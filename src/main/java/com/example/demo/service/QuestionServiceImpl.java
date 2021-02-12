package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final SurveyRepository surveyRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, SurveyRepository surveyRepository) {
        this.questionRepository = questionRepository;
        this.surveyRepository = surveyRepository;
    }

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

    @Override
    public Survey editQuestion(String questionID, String text) throws RuntimeException{
        Question question = questionRepository.findById(UUID.fromString(questionID)).orElseThrow(()->new RuntimeException("Вопрос с данным ID не найден"));
        question.setText(text);
        questionRepository.saveAndFlush(question);
        return question.getSurvey();
    }

    @Override
    public Survey deleteQuestion(String questionID) {
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
