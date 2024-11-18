package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "streak")
public class StreakEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long user;

    @Column(name = "activity_id")
    private Long activity;

    @Column(name = "current_streak",nullable = false)
    private int currentStreak;

    @Column(name = "max_streak",nullable = false)
    private int maxStreak;

    @Column(name = "last_check_in",nullable = false)
    private LocalDateTime lastCheckIn;
}
