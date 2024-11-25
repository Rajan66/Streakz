package com.example.backend.service.streak;

import com.example.backend.dto.StreakDto;
import com.example.backend.entity.StreakEntity;
import com.example.backend.mapper.impl.StreakMapper;
import com.example.backend.repository.StreakRepository;
import com.example.backend.service.activity.ActivityService;
import com.example.backend.service.authentication.UserDetailService;
import com.example.backend.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class StreakServiceImpl implements StreakService {

    private final StreakRepository streakRepository;
    private final StreakMapper streakMapper;
    private final UserService userService;
    private final ActivityService activityService;

    public StreakServiceImpl(StreakRepository streakRepository, StreakMapper streakMapper, UserService userService, ActivityService activityService) {
        this.streakRepository = streakRepository;
        this.streakMapper = streakMapper;
        this.userService = userService;
        this.activityService = activityService;
    }

    @Override
    public StreakDto save(StreakDto streakDto) {
        StreakEntity streakEntity = streakMapper.mapFrom(streakDto);
        if (userService.exists(streakEntity.getUser().getId()) && activityService.exists(streakEntity.getActivity().getId())) {
            StreakEntity savedStreakEntity = streakRepository.save(streakEntity);
            return streakMapper.mapTo(savedStreakEntity);
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
