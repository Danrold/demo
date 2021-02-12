package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * Класс реализующий шаблон проектирования Data Transfer Object
 * Необходим для передачи информации в запросе для редактирования сущности опрос
 * Используются аннотации Bean Validation для установки правил валидации
 * Так же использована аннотация @Data от Lombok для создания конструктора, геттеров и сеттеров
 * @author Ivan Medvedev
 */

@Data
public class EditRequestDTO {

    /**
     * Идентификатор редактируемого опроса. Принимается в виде String с последующей конвертацией в UUID
     */
    @NotNull(message = " не должно быть пустым")
    @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}", message = " не соответствует формату UUID")
    private String id;

    /**
     * Новое имя для редактируемого списка. Должно содержать от 3 до 255 символов
     */
    @NotBlank(message = " не должно быть пустым")
    @Size(min = 3, max = 255, message = " должно содержать от 3 до 255 символов")
    private String name;

    /**
     * Дата начала опроса. Принимается в формате Long и не должна быть раньше текущей даты
     */
    @NotNull(message = " не должно быть пустым")
    @FutureOrPresent(message = " должно содержать сегодняшнее число или дату, которая еще не наступила")
    private Date start;

    /**
     * Дата окончания опроса. Принимается в формате Long и не должна быть раньше текущей даты
     */
    @NotNull(message = " не должно быть пустым")
    @FutureOrPresent(message = " должно содержать сегодняшнее число или дату, которая еще не наступила")
    private Date end;

    /**
     * Флаг активности опроса. Принимается в формате String для конвертации в Boolean. Может принимать значения только true или false
     */
    @NotNull(message = " не должно быть пустым")
    @Pattern(regexp = "^true$|^false$", message = " должен принимать только true или false")
    private String isActive;
}
