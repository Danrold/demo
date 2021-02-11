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
    public List<Survey> getAll(String field, boolean isAscending) throws RuntimeException{
        if(field == null) throw new RuntimeException("Необходимо указать поле для сортировки");
        if(!field.equals("name") && !field.equals("startDate")) throw new RuntimeException("Сортировка доступна только по полям name, start_date");
        Sort sort;
        if(isAscending){
            sort = Sort.by(field).ascending();
        }else {
            sort = Sort.by(field).descending();
        }
        return surveyRepository.findAll(sort);
    }

    @Override
    public Survey create(String name) throws RuntimeException{
        if(name == null) throw new RuntimeException("Необходимо указать имя создаваемого опроса");
        Survey survey = new Survey(name);
        survey.setStartDate(new Date());
        survey.setEndDate(new Date());
        return surveyRepository.saveAndFlush(survey);
    }

    @Override
    public Survey edit(EditRequestDTO editRequest) throws RuntimeException {
        Survey survey = surveyRepository.findById(UUID.fromString(editRequest.getId())).orElseThrow(()->new RuntimeException("Опрос с данным ID не найден"));

        survey.setName(editRequest.getName());
        survey.setStartDate(new Date(editRequest.getStart()));
        survey.setEndDate(new Date(editRequest.getEnd()));
        survey.setActive(editRequest.getIsActive());

        return surveyRepository.saveAndFlush(survey);
    }

    @Override
    public void delete(String id) throws RuntimeException{
        if(id == null) throw new RuntimeException("Необходимо указать ID удаляемого опроса");
        UUID uuid;
        try{
            uuid = UUID.fromString(id);
        }catch (IllegalArgumentException exception){
            throw new RuntimeException("Ошибка формата ID");
        }
        Survey survey = surveyRepository.findById(uuid).orElseThrow(()->new RuntimeException("Опрос с данным ID не найден"));
        surveyRepository.delete(survey);
    }
}
