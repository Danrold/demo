package com.example.demo.controller;

import com.example.demo.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/demo")
public class SurveyRestController {

    SurveyService surveyService;

    @Autowired
    public SurveyRestController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping(value = "/get")
    public ResponseEntity<Object> getAll(){
        try{
            return new ResponseEntity<>(surveyService.getAll(), HttpStatus.OK);
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
    public ResponseEntity<Object> edit(){
        return new ResponseEntity<>("test", HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> delete(){
        return new ResponseEntity<>("test", HttpStatus.OK);
    }

}
