package com.example.demo.service;

import com.example.demo.dto.EditRequestDTO;
import com.example.demo.entity.Survey;

import java.util.List;

/**
 * Интерфейс описывающий методы для работы с опросами
 */
public interface SurveyService {

    List<Survey> getAll(String field, String isAscending);

    Survey create(String name);

    Survey edit(EditRequestDTO editRequest);

    void delete(String id);

}
