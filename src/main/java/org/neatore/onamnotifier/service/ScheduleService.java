package org.neatore.onamnotifier.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        List<Schedule> schedules = scheduleRepository.findSchedulesForClass(grade, classNum);
        return this.scheduleMapper.toDtoList(schedules);
    }

    public ScheduleDto.QueryScheduleResponse getSchedule(UUID id) {
        Optional<Schedule> response = this.scheduleRepository.findById(id);
        return response.map(this.scheduleMapper::toDto)
                .orElseThrow(() -> new QueryNotFoundException(id.toString()));
    }

    @Transactional
    public UUID createNewSchedule(ScheduleDto.PostRequest request) {
        Schedule newSchedule = new Schedule(
                request.title(),
                request.content(),
                request.grade(),
                request.classNum(),
                request.endDate()
        );
        this.scheduleRepository.save(newSchedule);
        return newSchedule.getId();
    }

    @Transactional
    public ScheduleDto.QueryScheduleResponse updateSchedule(UUID id, ScheduleDto.PostRequest putRequest) {
        Schedule schedule = this.scheduleRepository.findById(id)
                .orElseThrow(() -> new QueryNotFoundException(id.toString()));
        schedule.setTitle(putRequest.title());
        schedule.setContent(putRequest.content());
        schedule.setGrade(putRequest.grade());
        schedule.setClassNum(putRequest.classNum());
        schedule.setEndDate(putRequest.endDate());

        this.scheduleRepository.save(schedule);
        return scheduleMapper.toDto(schedule);
    }

    @Transactional
    public void deleteSchedule(UUID id) {
        this.scheduleRepository.deleteById(id);
    }
}
