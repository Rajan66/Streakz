package com.example.backend.service.streak;

import com.example.backend.dto.StreakDto;
import com.example.backend.entity.StreakEntity;
import com.example.backend.mapper.impl.StreakMapper;
import com.example.backend.repository.StreakRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class StreakServiceImpl implements StreakService {

    private final StreakRepository streakRepository;
    private final StreakMapper streakMapper;

    public StreakServiceImpl(StreakRepository streakRepository, StreakMapper streakMapper) {
        this.streakRepository = streakRepository;
        this.streakMapper = streakMapper;
    }

    @Override
    public StreakDto save(StreakDto streakDto) {
        return null;
    }

    @Override
    public StreakDto save(Long id) {
        StreakEntity existingStreakEntity = streakRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Streak not found with the id: " + id));
        if (streakRepository.existsById(id)) {
            boolean dateCheck = existingStreakEntity.getLastCheckIn().toLocalDate().equals(LocalDateTime.now().toLocalDate());
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
