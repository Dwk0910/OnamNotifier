package org.neatore.onamnotifier.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    @Query("SELECT s FROM Schedule s WHERE " +
            "(s.grade = :grade AND s.classNum = :classNum) OR " +
            "(s.grade = :grade AND s.classNum IS NULL) OR " +
            "(s.grade IS NULL AND s.classNum IS NULL)")
    List<Schedule> findSchedulesForClass(@Param("grade") Integer grade, @Param("classNum") Integer classNum);
}
