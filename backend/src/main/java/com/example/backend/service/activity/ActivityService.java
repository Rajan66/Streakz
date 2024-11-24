package com.example.backend.service.activity;

import com.example.backend.dto.ActivityDto;
import com.example.backend.service.GenericService;

public interface ActivityService extends GenericService<ActivityDto> {
    ActivityDto save(ActivityDto activityDto, Long id);
}
