package com.example.backend.config.impl;

import com.example.backend.config.Patcher;
import com.example.backend.entity.StreakEntity;
import com.example.backend.repository.StreakRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@Slf4j
public class StreakPatcher implements Patcher<StreakEntity> {

    private final StreakRepository streakRepository;

    public StreakPatcher(StreakRepository streakRepository) {
        this.streakRepository = streakRepository;
    }

    @Override
    public StreakEntity patch(StreakEntity existingStreak, StreakEntity incompleteStreak) throws IllegalAccessException {
        Class<?> streakEntityClass = StreakEntity.class;
        Field[] streakFields = streakEntityClass.getDeclaredFields();
        for (Field field : streakFields) {
            field.setAccessible(true);
            Object value = field.get(incompleteStreak);
            if (field.getName().equals("user") || field.getName().equals("activity")) {
                continue;
            }
            if (value != null) {
                log.info(value.toString());
                field.set(existingStreak, value);
            }
            field.setAccessible(false);
        }
        return streakRepository.save(existingStreak);

    }
}
