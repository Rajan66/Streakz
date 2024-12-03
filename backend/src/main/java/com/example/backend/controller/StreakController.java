package com.example.backend.controller;

import com.example.backend.dto.GlobalApiResponse;
import com.example.backend.dto.StreakDto;
import com.example.backend.service.streak.StreakService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/streak")
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

//    @PostMapping("")
//    public ResponseEntity<GlobalApiResponse> createStreak(@RequestBody StreakDto streakDto) {
//        log.info("StreakController: Create streak api called!");
//        streakDto.setCurrentStreak(0);
//        streakDto.setMaxStreak(0);
//        StreakDto savedStreakDto = streakService.save(streakDto);
//        if (savedStreakDto == null) {
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body(failureResponse("Streak creation failed", null));
//        }
//        return ResponseEntity.ok().body(successResponse("Streak created successfully", savedStreakDto));
//    }
//
    @PatchMapping("/{id}")
    public ResponseEntity<GlobalApiResponse> updateStreak(@PathVariable("id") Long id) {
        log.info("StreakController: Update streak api called!");
        StreakDto updatedStreakDto = streakService.save(id);
        if (updatedStreakDto == null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(failureResponse("Streak updation failed", null));
        }
        return ResponseEntity.ok().body(successResponse("Streak updated successfully", updatedStreakDto));
    }

    // Maybe I don't need this?
    // If I delete an activity, the streak should cascade
    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalApiResponse> deleteStreak(@PathVariable("id") Long id) {
        boolean deleteResponse = streakService.delete(id);
        if (!deleteResponse) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(failureResponse("Streak not found", null));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(successResponse("Streak deleted successfully", null));
    }
}
