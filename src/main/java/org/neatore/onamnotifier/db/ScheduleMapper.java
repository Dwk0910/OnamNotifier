package org.neatore.onamnotifier.db;

import org.mapstruct.Mapper;
import org.neatore.onamnotifier.dto.ScheduleDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    ScheduleDto.QueryScheduleResponse toDto(Schedule domain);
    List<ScheduleDto.QueryScheduleResponse> toDtoList(List<Schedule> domainList);
}
