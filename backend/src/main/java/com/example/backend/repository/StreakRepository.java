package com.example.backend.repository;

import com.example.backend.model.StreakEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreakRepository extends JpaRepository<StreakEntity, Long> {
}
