package ru.stankin.project.persistence.entity;

import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@lombok.Data
@Accessors(chain = true)
public class Entity {
    private long id;
    private String model;
    private String details;
    private String year;
}
