package com.example.backend.mapper.impl;

import com.example.backend.dto.StreakDto;
import com.example.backend.mapper.Mapper;
import com.example.backend.model.StreakEntity;
import org.modelmapper.ModelMapper;


public class StreakMapper implements Mapper<StreakEntity, StreakDto> {

    private final ModelMapper modelMapper;

    public StreakMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public StreakDto mapTo(StreakEntity streakEntity) {
        return modelMapper.map(streakEntity, StreakDto.class);
    }

    @Override
    public StreakEntity mapFrom(StreakDto streakDto) {
        return modelMapper.map(streakDto, StreakEntity.class);
    }
}
