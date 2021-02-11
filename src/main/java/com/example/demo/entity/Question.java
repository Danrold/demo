package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "index", nullable = false)
    private int index;

    @ManyToOne
    @JoinColumn(name = "survey", nullable = false)
    @JsonIgnore
    private Survey survey;

}
