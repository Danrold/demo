package com.example.demo.controller;

import com.example.demo.dto.EditRequestDTO;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.*;
import javax.validation.constraints.*;

/**
 * REST-Controller предоставляющий CRUD api для работы с Опросами
 * Так же реализованы методы для работы с сущностью Вопрос, входящей в сущность Опрос
 * Валидация осуществляется средствами Bean Validation
 * @author Ivan Medvedev
 */

@Validated
@RestController
@RequestMapping(value = "api/demo")
public class SurveyRestController {

    /**
     * Сервис осуществляющий логику работы с опросами
     */
    private final SurveyService surveyService;
    /**
     * Сервис осуществляющий логику работы с вопросами
     */
    private final QuestionService questionService;

    /**
     * @param surveyService Сервис для работы с опросами
     * @param questionService Сервис для работы с вопросами
     */
    @Autowired
    public SurveyRestController(SurveyService surveyService, QuestionService questionService) {
        this.surveyService = surveyService;
        this.questionService = questionService;
    }

    /**
     * Метод для получения списка существующих опросов
     * @param field Поле для сортировки. Доступна сортировка по имени (name) и по дате начала опроса (startDate)
     * @param isAscending Порядок сортировки. Если установлен флаг true сортировка выполняется по возрастанию, false - по убыванию
     * @return Список существующих опросов
     */
    @GetMapping(value = "/get")
    public ResponseEntity<Object> getAll(@RequestParam @NotBlank String field,
            @RequestParam @Pattern(regexp = "^true$|^false$", message = "Параметр isAscending должен принимать только true или false") String isAscending){
        try{
            return new ResponseEntity<>(surveyService.getAll(field, isAscending), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * Метод создания нового пустого опроса
     * @param name Имя создаваемого опроса, допустимо неуникальное значение
     * @return Созданный опрос
     */
    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestParam @NotBlank String name){
        try{
            return new ResponseEntity<>(surveyService.create(name), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * Метод изменения существующего опроса
     * @param requestDTO Объект содержащий в себе следующие поля
     *                   id - идентификатор изменяемого опроса
     *                   name - новое имя опроса
     *                   start - новая дата начала
     *                   end - новая дата окончания
     *                   isActive - флаг активности опроса
     * @return Обновленный опрос
     */
    @PutMapping(value = "/edit")
    public ResponseEntity<Object> edit(@Valid @RequestBody EditRequestDTO requestDTO){
        try{
            return new ResponseEntity<>(surveyService.edit(requestDTO), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * Метод удаляет существующий опрос
     * @param id Идентификатор удаляемого опроса
     * @return Сообщение об успешном удалении
     */
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> delete(@RequestParam @Pattern
            (regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}",
                    message = "Ошибка в формате ID") String id){
        try{
            surveyService.delete(id);
            return new ResponseEntity<>("Опрос успешно удален", HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * Метод добавляет новый вопрос в опрос по идентификатору
     * @param surveyID Идентификатор опроса
     * @param text Текст вопроса
     * @return Обновленный опрос
     */
    @PostMapping(value = "/question/add")
    public ResponseEntity<Object> addQuestion(@RequestParam @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}",
                    message = "Ошибка в формате ID") String surveyID, @RequestParam @NotBlank String text){
        return new ResponseEntity<>(questionService.addQuestion(surveyID, text), HttpStatus.OK);
    }

    /**
     * Метод изменяет текст существующего вопроса
     * @param questionID Идентификатор вопрос
     * @param text Новый текст вопроса
     * @return Обновленный опрос
     */
    @PutMapping(value = "/question/edit")
    public ResponseEntity<Object> editQuestion(@RequestParam @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}",
            message = "Ошибка в формате ID") String questionID, @RequestParam String text){
        return new ResponseEntity<>(questionService.editQuestion(questionID, text), HttpStatus.OK);
    }

    /**
     * Метод удаляет существующий вопрос
     * @param questionID Идентификатор удаляемого вопроса
     * @return Обновленный опрос
     */
    @DeleteMapping(value = "/question/delete")
    public ResponseEntity<Object> deleteQuestion(@RequestParam @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}",
            message = "Ошибка в формате ID") String questionID){
        return new ResponseEntity<>(questionService.deleteQuestion(questionID), HttpStatus.OK);
    }



    /**
     * Обработчик исключения - ConstraintViolationException
     * @param exception Исключение которое бросает Bean Validation когда в @RequestParam попадает невалидное значение
     * @return Сообщение об ошибке
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception){
        return new ResponseEntity<>("Ошибка валидации: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Обработчик исключения - MethodArgumentNotValidException
     * @param exception Исключение которое бросает Bean Validation когда в @RequestBody попадает невалидное значение
     * @return Сообщение об ошибке
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        return new ResponseEntity<>("Ошибка валидации. Поле " + exception.getFieldError().getField() + exception.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Обработчик исключения - EntityNotFoundException
     * @param exception Исключение которое может возникнуть, если по указанному ID не удалось найти сущность
     * @return Сообщение об ошибке
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Обработчик исключения - RuntimeException
     * @param exception Исключение которое может возникнуть при работе сервисного слоя
     * @return Сообщение об ошибке
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<Object> handleEntityNotFoundException(RuntimeException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
