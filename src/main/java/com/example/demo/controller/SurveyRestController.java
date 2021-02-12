package com.example.demo.controller;

import com.example.demo.dto.EditRequestDTO;
import com.example.demo.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import javax.validation.constraints.*;

@Validated
@RestController
@RequestMapping(value = "api/demo")
public class SurveyRestController {

    private final SurveyService surveyService;

    @Autowired
    public SurveyRestController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping(value = "/get")
    public ResponseEntity<Object> getAll(@RequestParam @NotBlank String field,
            @RequestParam @Pattern(regexp = "^true$|^false$", message = "Параметр isAscending должен принимать только true или false") String isAscending){
        try{
            return new ResponseEntity<>(surveyService.getAll(field, isAscending), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestParam @NotBlank String name){
        try{
            return new ResponseEntity<>(surveyService.create(name), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping(value = "/edit")
    public ResponseEntity<Object> edit(@Valid @RequestBody EditRequestDTO requestDTO){
        try{
            return new ResponseEntity<>(surveyService.edit(requestDTO), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

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

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception){
        return new ResponseEntity<>("Ошибка валидации: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        return new ResponseEntity<>("Ошибка валидации. Поле " + exception.getFieldError().getField() + exception.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

}
