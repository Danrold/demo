package com.example.demo.dto;

import lombok.Data;

@Data
public class EditRequestDTO {

    private String id;
    private String name;
    private Long start;
    private Long end;
    private Boolean isActive;
}
