package com.example.backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GenericService<T> {
    T save(T t);

    T findOne(Long id);

    Page<T> findAll(Pageable pageable);

    boolean exists(Long id);

    boolean delete(Long id);
}
