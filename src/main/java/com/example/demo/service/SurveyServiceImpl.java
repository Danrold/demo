package com.example.demo.service;

import com.example.demo.dto.EditRequestDTO;
import com.example.demo.entity.Survey;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

/**
 * Имплементация интерфейса SurveyService
 * @author Ivan Medvedev
 */
@Service
public class SurveyServiceImpl implements SurveyService{

    /**
     * JPA репозиторий для работы с сущностью Опрос
     */
    private final SurveyRepository surveyRepository;

    /**
     * Конструктор класса
     * @param surveyRepository JPA репозиторий для работы с сущностью Опрос
     */
    @Autowired
    public SurveyServiceImpl(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    /**
     * Метод возвращает отсортированный список опросов
     * @param field Поле для сортировки (доступные значения: name, startDate)
     * @param isAscending Порядок сортировки. Если установлен флаг true сортировка выполняется по возрастанию, false - по убыванию
     * @return Список опросов
     * @throws RuntimeException Бросается если значение field не соответствует доступным значениям
     */
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

    /**
     * Метод создает новый опрос
     * @param name Имя опроса
     * @return Созданный опрос
     */
    @Override
    public Survey create(String name){
        Survey survey = new Survey(name);
        return surveyRepository.saveAndFlush(survey);
    }

    /**
     * Метод редактирует существующий опрос
     * @see com.example.demo.dto.EditRequestDTO
     * @param editRequest объект содержащий информацию для редактирования
     * @return Обновленный опрос
     * @throws RuntimeException Бросается если опрос с указанным идентификатором не был найден, а так же если дата окончания опроса укзана раньше даты начала опроса
     */
    @Override
    public Survey edit(EditRequestDTO editRequest) throws RuntimeException {
        Survey survey = surveyRepository.findById(UUID.fromString(editRequest.getId())).orElseThrow(()->new EntityNotFoundException("Опрос с данным ID не найден"));

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

    /**
     * Метод удаляет существующий опрос
     * @param id Идентификатор опроса
     * @throws EntityNotFoundException Бросается если опрос с указанным идентификатором не был найден
     */
    @Override
    public void delete(String id) throws EntityNotFoundException{
        UUID uuid = UUID.fromString(id);
        Survey survey = surveyRepository.findById(uuid).orElseThrow(()->new EntityNotFoundException("Опрос с данным ID не найден"));
        surveyRepository.delete(survey);
    }
}
