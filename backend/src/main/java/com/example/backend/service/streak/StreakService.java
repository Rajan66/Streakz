package com.example.backend.service.streak;

import com.example.backend.dto.StreakDto;
import com.example.backend.service.GenericService;

public interface StreakService extends GenericService<StreakDto> {
    StreakDto save(Long id);
}
