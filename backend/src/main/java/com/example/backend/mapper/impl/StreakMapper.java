package com.example.backend.mapper.impl;

import com.example.backend.dto.ActivityDto;
import com.example.backend.dto.StreakDto;
import com.example.backend.mapper.Mapper;
import com.example.backend.model.ActivityEntity;
import com.example.backend.model.StreakEntity;
import org.modelmapper.ModelMapper;


public class StreakMapper implements Mapper<StreakEntity, StreakDto> {

    private final ModelMapper modelMapper;

    public StreakMapper(ModelMapper modelMapper) {
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
