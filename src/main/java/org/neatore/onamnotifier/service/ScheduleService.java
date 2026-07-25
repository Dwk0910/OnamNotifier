package org.neatore.onamnotifier.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import org.neatore.onamnotifier.db.Schedule;
import org.neatore.onamnotifier.db.ScheduleMapper;
import org.neatore.onamnotifier.db.ScheduleRepository;

import org.neatore.onamnotifier.dto.ScheduleDto;

import org.neatore.onamnotifier.exception.QueryNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    public List<ScheduleDto.QueryScheduleResponse> getSchedules(Integer grade, Integer classNum) {
        return this.scheduleMapper.toDtoList(
                this.scheduleRepository.findSchedulesByGradeAndClassNum(grade, classNum)
        );
    }

    public ScheduleDto.QueryScheduleResponse getSchedule(UUID id) {
        Optional<Schedule> response = this.scheduleRepository.findById(id);
        return response.map(this.scheduleMapper::toDto)
                .orElseThrow(() -> new QueryNotFoundException("Schedule not found with id: " + id));
    }
}
