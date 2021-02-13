package com.example.demo.service;

import com.example.demo.dto.EditRequestDTO;
import com.example.demo.entity.Survey;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Интерфейс описывающий методы для работы с опросами
 * @author Ivan Medvedev
 */
public interface SurveyService {

    /**
     * Метод возвращает отсортированный список опросов
     * @param field Поле для сортировки (доступные значения: name, startDate)
     * @param isAscending Порядок сортировки. Если установлен флаг true сортировка выполняется по возрастанию, false - по убыванию
     * @return Список опросов
     * @throws RuntimeException Бросается если значение field не соответствует доступным значениям
     */
    List<Survey> getAll(String field, String isAscending) throws RuntimeException;

    /**
     * Метод создает новый опрос
     * @param name Имя опроса
     * @return Созданный опрос
     */
    Survey create(String name);

    /**
     * Метод редактирует существующий опрос
     * @see com.example.demo.dto.EditRequestDTO
     * @param editRequest объект содержащий информацию для редактирования
     * @return Обновленный опрос
     * @throws RuntimeException Бросается если опрос с указанным идентификатором не был найден, а так же если дата окончания опроса укзана раньше даты начала опроса
     */
    Survey edit(EditRequestDTO editRequest) throws RuntimeException;

    /**
     * Метод удаляет существующий опрос
     * @param id Идентификатор опроса
     * @throws EntityNotFoundException Бросается если опрос с указанным идентификатором не был найден
     */
    void delete(String id) throws EntityNotFoundException;

}
