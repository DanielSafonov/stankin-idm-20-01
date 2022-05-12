package ru.stankin.project.persistence.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.stankin.project.persistence.entity.Entity;

import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Repository
public class Repository {
    public static final long PAGE_SIZE = 5;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Entity> findAll(int pageNum) {
        return jdbcTemplate.query(
                "select * from data limit :limit offset :offset",
                Map.of("limit", PAGE_SIZE, "offset", pageNum * PAGE_SIZE),
                new BeanPropertyRowMapper<Entity>(Entity.class)
        );
    }

    public List<Entity> findAll() {
        return jdbcTemplate.query(
                "select * from data",
                new BeanPropertyRowMapper<Entity>(Entity.class)
        );
    }
}
