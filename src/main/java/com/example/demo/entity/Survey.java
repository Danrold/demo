package com.example.demo.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

/**
 * Класс сущности Опрос, соответствует таблице surveys
 * Используются аннотации Lombok для генерации конструктора без параметров, а так же геттеров и сеттеров
 * @author Ivan Medvedev
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "surveys")
public class Survey {

    /**
     * Идентификатор вопроса в формате UUID
     * Генерируется средствами Hibernate
     * Первичный ключ, уникален и не может быть null
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    /**
     * Название опроса
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Дата начала опроса
     */
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    /**
     * Дата окончания опроса
     */
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    /**
     * Флаг активности вопроса
     */
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    /**
     * Список вопросов в текущем опросе
     */
    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<Question> questions;

    /**
     * Конструктор класса. Параметры isActive, startDate и endDate задаются по умолчанию
     * @param name Название опроса
     */
    public Survey(String name) {
        this.name = name;
        isActive = false;
        startDate = new Date();
        endDate = new Date();
    }
}
