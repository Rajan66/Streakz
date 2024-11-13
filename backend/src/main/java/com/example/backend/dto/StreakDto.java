package com.example.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StreakDto {

    private Long id;

    private Long userId;

    private Long activityId;

    @NotBlank
    private int currentStreak;

    @NotBlank
    private int maxStreak;

    private LocalDateTime lastCheckIn;
}
