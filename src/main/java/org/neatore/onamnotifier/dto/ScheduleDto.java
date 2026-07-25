package org.neatore.onamnotifier.dto;

public class ScheduleDto {
    public record QueryScheduleResponse(
            String id,
            String title,
            String content,
            Long endDate
    ) {}
}
