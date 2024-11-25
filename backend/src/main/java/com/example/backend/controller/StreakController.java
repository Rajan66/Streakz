package com.example.backend.controller;

import com.example.backend.dto.GlobalApiResponse;
import com.example.backend.dto.StreakDto;
import com.example.backend.service.streak.StreakService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "api/streak")
public class StreakController extends BaseController {
    private final StreakService streakService;

    public StreakController(StreakService streakService) {
        this.streakService = streakService;
    }
    //TODO: api testing
    @GetMapping("/{id}")
    public ResponseEntity<GlobalApiResponse> getStreak(@PathVariable("id") Long id) {
        StreakDto streakDto = streakService.findOne(id);
        if (streakDto == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(failureResponse("Streak not found", null));
        }
        return ResponseEntity.ok().body(successResponse("Streak found", streakDto));
    }

    @PostMapping("")
    public ResponseEntity<GlobalApiResponse> createStreak(@RequestBody StreakDto streakDto) {
        StreakDto savedStreakDto = streakService.save(streakDto);
        if (streakDto == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(failureResponse("Streak creation failed", null));
        }
        return ResponseEntity.ok().body(successResponse("Streak created successfully", savedStreakDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalApiResponse> deleteStreak(@PathVariable("id") Long id) {
        boolean deleteResponse = streakService.delete(id);
        if (!deleteResponse) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(failureResponse("Streak not found", null));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(successResponse("Streak deleted successfullly", null));
    }
}
