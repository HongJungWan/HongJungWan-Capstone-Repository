package com.example.demo.domain.entity;


import com.example.demo.domain.value.ReportStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "report")
@Getter
public class Report {

    @Column(name = "report_id")
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "report_date", nullable = false)
    private LocalDateTime reportDate;

    @Column(name = "car_number", nullable = false)
    private String carNumber;

    @Column(name = "cause", nullable = false)
    private String cause;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id", nullable = false)
    private College college;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status = ReportStatus.PROCESS;

    @Builder(builderMethodName = "createReport")
    public Report(LocalDateTime reportDate, String carNumber, String cause, ReportStatus status, User user, College college) {
        this.reportDate = reportDate;
        this.carNumber = carNumber;
        this.cause = cause;
        this.status = status;
        this.college = college;
        this.user = user;
    }

    private void setStatus(ReportStatus process) {
        this.status = process;
    }

    public void control() {setStatus(ReportStatus.PROCESS);}

}
