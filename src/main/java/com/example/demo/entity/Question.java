package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

/**
 * Класс сущности Вопрос, соответствует таблице questions
 * Используются аннотации Lombok для генерации конструктора без параметров, а так же геттеров и сеттеров
 * @author Ivan Medvedev
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class Question {

    /**
     * Идентификатор вопроса в формате UUID
     * Генерируется средствами Hibernate
     * Первичный ключ, уникален и не может быть null
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    /**
     * Текст вопроса
     */
    @Column(name = "text", nullable = false)
    private String text;

    /**
     * Порядковй номер вопроса в опросе
     */
    @Column(name = "index", nullable = false)
    private int index;

    /**
     * Ссылка на опрос, к которому принадлежит сущность
     */
    @ManyToOne
    @JoinColumn(name = "survey", nullable = false)
    @JsonIgnore
    private Survey survey;

    /**
     * Конструктор класса
     * @param text Текст вопроса
     * @param index Порядковый номер вопроса
     * @param survey Родительская сущность (опрос) для данного вопроса
     */
    public Question(String text, int index, Survey survey) {
        this.text = text;
        this.index = index;
        this.survey = survey;
    }
}
