package com.example.backend.service.activity;

import com.example.backend.dto.ActivityDto;
import com.example.backend.mapper.impl.ActivityMapper;
import com.example.backend.model.ActivityEntity;
import com.example.backend.repository.ActivityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    public ActivityServiceImpl(ActivityRepository activityRepository, ActivityMapper activityMapper) {
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
    }

    @Override
    public ActivityDto save(ActivityDto activityDto) {
        ActivityEntity activityEntity = activityMapper.mapFrom(activityDto);
        ActivityEntity savedActivityEntity = activityRepository.save(activityEntity);
        return activityMapper.mapTo(savedActivityEntity);
    }

    @Override
    public ActivityDto findOne(Long id) {
        ActivityEntity foundActivityEntity = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found"));
        return activityMapper.mapTo(foundActivityEntity);
    }

    @Override
    public Page<ActivityDto> findAll(Pageable pageable) {
        Page<ActivityEntity> activityEntities = activityRepository.findAll(pageable);
        return activityEntities.map(activityMapper::mapTo);
    }

    @Override
    public boolean exists(Long id) {
        return activityRepository.existsById(id);
    }

    @Override
    public boolean delete(Long id) {
        if (activityRepository.existsById(id)) {
            activityRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
