package com.example.demo.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/demo")
public class SurveyRestController {

    @GetMapping(value = "/get")
    public ResponseEntity<Object> getAll(){
        return new ResponseEntity<>("test", HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(){
        return new ResponseEntity<>("test", HttpStatus.OK);
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
