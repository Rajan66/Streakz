package com.example.backend.config.impl;

import com.example.backend.config.Patcher;
import com.example.backend.entity.ActivityEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Slf4j
@Component
public class ActivityPatcher implements Patcher<ActivityEntity> {
    @Override
    public void patch(ActivityEntity existingActivity, ActivityEntity incompleteActivity) throws IllegalAccessException {
        Class<?> activityEntityClass = ActivityEntity.class;
        Field[] activityFields = activityEntityClass.getDeclaredFields();
        for (Field field : activityFields) {
            field.setAccessible(true);
            Object value = field.get(incompleteActivity);
            if(field.getName().equals("user")){
                continue;
            }
            if (value == null) {
                log.info(field.getName());
                field.set(existingActivity, null);
            } else if (value != null) {
                field.set(existingActivity, value);
            }
            field.setAccessible(false);
        }
    }
}
