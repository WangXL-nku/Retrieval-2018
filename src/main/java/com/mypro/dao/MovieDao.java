package com.mypro.dao;

import com.mypro.domain.Movie;

import java.util.List;

public interface MovieDao {
    List<Movie> findAll();
    Movie findById(Integer id);
}
