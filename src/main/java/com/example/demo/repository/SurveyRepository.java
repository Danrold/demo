package com.example.demo.repository;

import com.example.demo.entity.Survey;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

/**
 * JPA репозиторий предоставляющий api запросов к базе данных для объектов Survey
 */

public interface SurveyRepository extends JpaRepository<Survey, UUID> {

    /**
     * @param sort Параметры для сортировки
     * @return Список сущностей Survey согласно переданным параметрам
     */
    List<Survey> findAll(Sort sort);

}
