package org.neatore.onamnotifier.controller;

import org.neatore.onamnotifier.dto.ScheduleDto;
import org.neatore.onamnotifier.service.ScheduleService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class SecheduleController {
    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<ScheduleDto.QueryScheduleResponse>> getSchedules(
            @RequestParam Integer grade,
            @RequestParam Integer classNum
    ) {
        return ResponseEntity.ok(this.scheduleService.getSchedules(grade, classNum));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDto.QueryScheduleResponse> getSchedule(@PathVariable UUID id) {
        return ResponseEntity.ok(this.scheduleService.getSchedule(id));
    }
}
