package com.example.demo.service;

import com.example.demo.entity.Survey;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface SurveyService {

    List<Survey> getAll();

    Survey create(String name);

    Survey edit(Survey survey);

    void delete(Survey survey);

}
