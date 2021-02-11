package com.example.demo.service;

import com.example.demo.entity.Survey;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SurveyServiceImpl implements SurveyService{

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public SurveyServiceImpl(SurveyRepository surveyRepository, QuestionRepository questionRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Survey> getAll() {
        return surveyRepository.findAll(Sort.by("name"));
    }

    @Override
    public Survey create(String name) {
        Survey survey = new Survey(name);
        survey.setStartDate(new Date());
        survey.setEndDate(new Date());
        return surveyRepository.saveAndFlush(survey);
    }

    @Override
    public Survey edit(Survey survey) {
        return null;
    }

    @Override
    public void delete(Survey survey) {

    }
}
