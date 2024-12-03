package com.example.backend.service.streak;

import com.example.backend.config.impl.StreakPatcher;
import com.example.backend.dto.StreakDto;
import com.example.backend.entity.ActivityEntity;
import com.example.backend.entity.StreakEntity;
import com.example.backend.entity.UserEntity;
import com.example.backend.mapper.impl.StreakMapper;
import com.example.backend.repository.ActivityRepository;
import com.example.backend.repository.StreakRepository;
import com.example.backend.service.activity.ActivityService;
import com.example.backend.service.authentication.UserDetailService;
import com.example.backend.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Service
public class StreakServiceImpl implements StreakService {

    private final StreakRepository streakRepository;
    private final StreakMapper streakMapper;
    private final UserService userService;
    private final ActivityService activityService;
    private final ActivityRepository activityRepository;
    private final StreakPatcher patcher;

    public StreakServiceImpl(StreakRepository streakRepository,
                             StreakMapper streakMapper,
                             UserService userService,
                             ActivityService activityService,
                             ActivityRepository activityRepository,
                             StreakPatcher patcher) {
        this.streakRepository = streakRepository;
        this.streakMapper = streakMapper;
        this.userService = userService;
        this.activityService = activityService;
        this.activityRepository = activityRepository;
        this.patcher = patcher;
    }

    @Override
    public StreakDto save(StreakDto streakDto) {
        StreakEntity streakEntity = streakMapper.mapFrom(streakDto);

        UserEntity userEntity = userService.findOne(streakEntity.getUser().getId());
        ActivityEntity activityEntity = activityRepository
                .findById(streakEntity.getActivity().getId())
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        if (userService.exists(streakEntity.getUser().getId()) && activityService.exists(streakEntity.getActivity().getId())) {
            StreakEntity savedStreakEntity = streakRepository.save(streakEntity);
            savedStreakEntity.setUser(userEntity);
            savedStreakEntity.setActivity(activityEntity);
            return streakMapper.mapTo(savedStreakEntity);
        }

        return null;
    }

    @Override
    public StreakDto save(Long id) {
        StreakEntity existingStreakEntity = streakRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Streak not found with the id: " + id));
        if (streakRepository.existsById(id)) {
            boolean dateCheck = existingStreakEntity.getLastCheckIn().toLocalDate().equals(LocalDateTime.now().toLocalDate());
            log.info(String.valueOf(existingStreakEntity.getLastCheckIn().toLocalDate()));
            log.info(String.valueOf(LocalDateTime.now().toLocalDate()));
            log.info("Streak Service, DateCheck Patch Method: {}", dateCheck);
            if (!dateCheck) {
                existingStreakEntity.setCurrentStreak(existingStreakEntity.getCurrentStreak() + 1);
                existingStreakEntity.setMaxStreak(existingStreakEntity.getMaxStreak() + 1);
                streakRepository.save(existingStreakEntity);
                return streakMapper.mapTo(existingStreakEntity);
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public StreakDto findOne(Long id) {
        StreakEntity streakEntity = streakRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Streak not found"));
        return streakMapper.mapTo(streakEntity);
    }

    @Override
    public Page<StreakDto> findAll(Pageable pageable) {
        Page<StreakEntity> streakEntities = streakRepository.findAll(pageable);
        return streakEntities.map(streakMapper::mapTo);
    }

    @Override
    public boolean exists(Long id) {
        return streakRepository.existsById(id);
    }

    @Override
    public boolean delete(Long id) {
        if (streakRepository.existsById(id)) {
            streakRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
