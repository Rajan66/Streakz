package com.example.backend.config;

public interface Patcher<T> {
    T patch(T existingEntity, T incompleteEntity) throws IllegalAccessException;
}
