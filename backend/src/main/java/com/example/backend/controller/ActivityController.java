package com.example.backend.controller;

import com.example.backend.dto.ActivityDto;
import com.example.backend.dto.GlobalApiResponse;
import com.example.backend.service.activity.ActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/api/activity")
public class ActivityController extends BaseController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }


    @PostMapping("")
    public ResponseEntity<GlobalApiResponse> createActivity(ActivityDto activityDto) {
        ActivityDto savedActivityDto = activityService.save(activityDto);
        if (savedActivityDto == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(failureResponse("Activity creation failed", null));
        }
        return ResponseEntity.ok(
                successResponse("Activity created successfully", savedActivityDto)
        );
    }
}
