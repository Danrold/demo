package com.example.demo.repository;

import com.example.demo.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * JPA репозиторий предоставляющий api запросов к базе данных для объектов Question
 */

public interface QuestionRepository extends JpaRepository<Question, UUID> {

}
