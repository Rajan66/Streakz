package com.example.backend.controller;

import com.example.backend.dto.ActivityDto;
import com.example.backend.dto.GlobalApiResponse;
import com.example.backend.service.activity.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/activity")
public class ActivityController extends BaseController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }


    //    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("")
    public ResponseEntity<GlobalApiResponse> createActivity(@RequestBody ActivityDto activityDto) {
        log.info("Activity DTO, RequestBody : " + activityDto.toString());
        ActivityDto savedActivityDto = activityService.save(activityDto);
        if (savedActivityDto == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(failureResponse("Activity creation failed", null));
        }
        log.info("Saved Activity: " + savedActivityDto.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse("Activity created successfully", savedActivityDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalApiResponse> getActivity(@PathVariable("id") Long id) {
        log.info("Activity ID : " + id);
        ActivityDto activityDto = activityService.findOne(id);

        if (activityDto == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(failureResponse("Activity not found", null));
        }

        return ResponseEntity.ok().body(successResponse("Activity retrieved successfully", activityDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GlobalApiResponse> updateActivity(@PathVariable("id") Long id, @RequestBody ActivityDto activityDto) {
        log.info("Request activity: " + activityDto.toString());
        ActivityDto updatedActivityDto = activityService.save(activityDto, id);
        if (updatedActivityDto == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(failureResponse("Activity not found", null));
        }
        return ResponseEntity.ok().body(successResponse("Activity updated successfully", updatedActivityDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalApiResponse> deleteActivity(@PathVariable("id") Long id) {
        log.info("Delete Activity, ID : " + id);
        boolean deleteResponse = activityService.delete(id);
        if (!deleteResponse) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(failureResponse("Activity not found", null));
        }
        /* Well, I thought of using NO_CONTENT instead
         * but, it is not a common practice to send a body in status code 204
         * so the alternative is to either us ok:200 or accepted:202 if we want to send a body
         */
        return ResponseEntity.ok().body(successResponse("Activity deleted successfully", null));
    }
}
