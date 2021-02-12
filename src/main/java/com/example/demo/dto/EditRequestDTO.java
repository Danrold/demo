package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class EditRequestDTO {

    @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}", message = " не соответствует формату UUID")
    private String id;
    @NotBlank(message = " не должно быть пустым")
    @Size(min = 3, max = 255, message = " должно содержать от 3 до 255 символов")
    private String name;

    @FutureOrPresent(message = " должно содержать сегодняшнее число или дату, которая еще не наступила")
    private Date start;
    @FutureOrPresent(message = " должно содержать сегодняшнее число или дату, которая еще не наступила")
    private Date end;

    @Pattern(regexp = "^true$|^false$", message = " должен принимать только true или false")
    private String isActive;
}
