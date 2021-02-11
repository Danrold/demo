package com.example.demo.repository;

import com.example.demo.entity.Survey;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface SurveyRepository extends JpaRepository<Survey, UUID> {

    List<Survey> findAll(Sort sort);

}
