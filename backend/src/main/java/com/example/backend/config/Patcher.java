package com.example.backend.config;

public interface Patcher<T> {
    void patch(T existingEntity, T incompleteEntity) throws IllegalAccessException;
}
