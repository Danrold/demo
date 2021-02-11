package com.example.demo.controller;

import com.example.demo.dto.EditRequestDTO;
import com.example.demo.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/demo")
public class SurveyRestController {

    private final SurveyService surveyService;

    @Autowired
    public SurveyRestController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping(value = "/get")
    public ResponseEntity<Object> getAll(@RequestParam String field, @RequestParam Boolean isAscending){
        try{
            return new ResponseEntity<>(surveyService.getAll(field, isAscending), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestParam String name){
        try{
            return new ResponseEntity<>(surveyService.create(name), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping(value = "/edit")
    public ResponseEntity<Object> edit(@RequestBody EditRequestDTO requestDTO){
        try{
            return new ResponseEntity<>(surveyService.edit(requestDTO), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> delete(@RequestParam String id){
        try{
            surveyService.delete(id);
            return new ResponseEntity<>("Опрос успешно удален", HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
