package com.example.backend.service.activity;

import com.example.backend.config.impl.ActivityPatcher;
import com.example.backend.dto.ActivityDto;
import com.example.backend.entity.StreakEntity;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.mapper.impl.ActivityMapper;
import com.example.backend.entity.ActivityEntity;
import com.example.backend.entity.UserEntity;
import com.example.backend.repository.ActivityRepository;
import com.example.backend.repository.StreakRepository;
import com.example.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    private final UserRepository userRepository;
    private final ActivityPatcher patcher;
    private final StreakRepository streakRepository;


    public ActivityServiceImpl(ActivityRepository activityRepository, ActivityMapper activityMapper, UserRepository userRepository, ActivityPatcher patcher, StreakRepository streakRepository) {
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
        this.userRepository = userRepository;
        this.streakRepository = streakRepository;
        this.patcher = patcher;
    }

    @Override
    public ActivityDto save(ActivityDto activityDto) {
        ActivityEntity activityEntity = activityMapper.mapFrom(activityDto);

        Long userId = activityEntity.getUser().getId();
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        activityEntity.setUser(userEntity);
        ActivityEntity savedActivityEntity = activityRepository.save(activityEntity);

        StreakEntity streakEntity = new StreakEntity();
        streakEntity.setActivity(savedActivityEntity);
        streakEntity.setCurrentStreak(0);
        streakEntity.setMaxStreak(0);
        streakEntity.setLastCheckIn(null);

        streakRepository.save(streakEntity);

        log.info(activityMapper.mapTo(savedActivityEntity).toString());
        return activityMapper.mapTo(savedActivityEntity);
    }


    @Override
    public ActivityDto save(ActivityDto activityDto, Long id) {
        ActivityDto existingActivityDto = findOne(id);
        if (activityDto.getTitle() == null) {
            activityDto.setTitle(existingActivityDto.getTitle());
        }

        ActivityEntity activityEntity = activityMapper.mapFrom(activityDto);
        ActivityEntity existingActivityEntity = activityMapper.mapFrom(existingActivityDto);
        activityEntity.setId(id);

        log.info("Existing Activity Entity:" + existingActivityEntity);

        try {
            patcher.patch(existingActivityEntity, activityEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return activityMapper.mapTo(existingActivityEntity);
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
