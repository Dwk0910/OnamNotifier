package org.neatore.onamnotifier.dto;

import java.time.LocalDateTime;

public class ScheduleDto {
    public record QueryScheduleResponse(
            String id,
            String title,
            String content,
            Integer grade,
            Integer classNum,
            LocalDateTime endDate
    ) {}

    public record PostRequest(
            String title,
            String content,
            Integer grade,
            Integer classNum,
            LocalDateTime endDate
    ) {}
}
