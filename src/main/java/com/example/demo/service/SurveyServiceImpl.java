package com.example.demo.service;

import com.example.demo.dto.EditRequestDTO;
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

    //private final int MILLISECONDS_IN_HOUR = 3600000;

    @Autowired
    public SurveyServiceImpl(SurveyRepository surveyRepository, QuestionRepository questionRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Survey> getAll(String field, String isAscending) throws RuntimeException{
        if(!field.equals("name") && !field.equals("startDate")) throw new RuntimeException("Сортировка доступна только по полям name, startDate");
        Sort sort;
        if(Boolean.parseBoolean(isAscending)){
            sort = Sort.by(field).ascending();
        }else {
            sort = Sort.by(field).descending();
        }
        return surveyRepository.findAll(sort);
    }

    @Override
    public Survey create(String name){
        Survey survey = new Survey(name);
        return surveyRepository.saveAndFlush(survey);
    }

    @Override
    public Survey edit(EditRequestDTO editRequest) throws RuntimeException {
        Survey survey = surveyRepository.findById(UUID.fromString(editRequest.getId())).orElseThrow(()->new RuntimeException("Опрос с данным ID не найден"));

        Date start = editRequest.getStart();
        Date end = editRequest.getEnd();

        if(start.after(end)){
            throw new RuntimeException("Дата начала опроса не может быть поже даты завершения опроса");
        }

        survey.setName(editRequest.getName());
        survey.setStartDate(editRequest.getStart());
        survey.setEndDate(editRequest.getEnd());
        survey.setActive(Boolean.parseBoolean(editRequest.getIsActive()));

        return surveyRepository.saveAndFlush(survey);
    }

    @Override
    public void delete(String id) throws RuntimeException{
        UUID uuid = UUID.fromString(id);
        Survey survey = surveyRepository.findById(uuid).orElseThrow(()->new RuntimeException("Опрос с данным ID не найден"));
        surveyRepository.delete(survey);
    }
}
