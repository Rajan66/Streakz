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
public class ActivityDto {

    private Long id;

    private Long userId;

    @NotBlank
    private String title;

    private String status;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
