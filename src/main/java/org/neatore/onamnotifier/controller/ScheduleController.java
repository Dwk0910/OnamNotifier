package org.neatore.onamnotifier.controller;

import org.neatore.onamnotifier.annotation.PublicAccess;
import org.neatore.onamnotifier.dto.ScheduleDto;
import org.neatore.onamnotifier.service.ScheduleService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping
    @PublicAccess
    public ResponseEntity<List<ScheduleDto.QueryScheduleResponse>> getSchedules(
            @RequestParam(required = false) Integer grade,
            @RequestParam(required = false) Integer classNum
    ) {
        if (grade != null && classNum != null) return ResponseEntity.ok(this.scheduleService.getSchedules(grade, classNum));
        else if (grade != null) return ResponseEntity.ok(this.scheduleService.getSchedules(grade));
        else return ResponseEntity.ok(this.scheduleService.getPublicSchedules());
    }

    @GetMapping("/{id}")
    @PublicAccess
    public ResponseEntity<ScheduleDto.QueryScheduleResponse> getSchedule(@PathVariable UUID id) {
        return ResponseEntity.ok(this.scheduleService.getSchedule(id));
    }

    @PostMapping
    public ResponseEntity<Void> createSchedule(@RequestBody ScheduleDto.PostRequest postRequest) {
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(this.scheduleService.createNewSchedule(postRequest))
                        .toUri()
        ).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDto.QueryScheduleResponse> updateSchedule(
            @PathVariable UUID id,
            @RequestBody ScheduleDto.PostRequest putRequest
    ) {
        return ResponseEntity.ok(this.scheduleService.updateSchedule(id, putRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable UUID id) {
        this.scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
