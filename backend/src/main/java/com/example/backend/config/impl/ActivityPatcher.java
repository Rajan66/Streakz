package com.example.backend.config.impl;

import com.example.backend.config.Patcher;
import com.example.backend.entity.ActivityEntity;
import com.example.backend.repository.ActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Slf4j
@Component
public class ActivityPatcher implements Patcher<ActivityEntity> {
    private final ActivityRepository activityRepository;

    public ActivityPatcher(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public ActivityEntity patch(ActivityEntity existingActivity, ActivityEntity incompleteActivity) throws IllegalAccessException {
        Class<?> activityEntityClass = ActivityEntity.class;
        Field[] activityFields = activityEntityClass.getDeclaredFields();
        for (Field field : activityFields) {
            field.setAccessible(true);
            Object value = field.get(incompleteActivity);
            if (field.getName().equals("user")) {
                continue;
            }
            if (value != null) {
                log.info(value.toString());
                field.set(existingActivity, value);
            }
            field.setAccessible(false);
        }
        return activityRepository.save(existingActivity);
    }
}
