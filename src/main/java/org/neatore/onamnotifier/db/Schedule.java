package org.neatore.onamnotifier.db;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {
    public Schedule(String title, String content, Integer grade, Integer classNum, LocalDateTime endDate) {
        this.title = title;
        this.content = content;
        this.grade = grade;
        this.classNum = classNum;
        this.endDate = endDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false)
    private String content;

    @Setter
    @Column(nullable = false)
    private LocalDateTime endDate;

    @Setter
    @Column(nullable = false)
    private Integer grade;

    @Setter
    @Column(nullable = false)
    private Integer classNum;
}
