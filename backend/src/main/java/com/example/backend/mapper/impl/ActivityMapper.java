package com.example.backend.mapper.impl;

import com.example.backend.dto.ActivityDto;
import com.example.backend.mapper.Mapper;
import com.example.backend.model.ActivityEntity;
import org.modelmapper.ModelMapper;

public class ActivityMapper implements Mapper<ActivityEntity, ActivityDto> {

    private final ModelMapper modelMapper;

    public ActivityMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ActivityDto mapTo(ActivityEntity activityEntity) {
        return modelMapper.map(activityEntity, ActivityDto.class);
    }

    @Override
    public ActivityEntity mapFrom(ActivityDto activityDto) {
        return modelMapper.map(activityDto, ActivityEntity.class);
    }
}
