package com.example.demo.domain.entity;


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
    private Long report_id;

    @Column(name = "report_date", nullable = false)
    private LocalDateTime reportDate;

    @Column(name = "car_number", nullable = false)
    private String carNumber;

    @Column(name = "cause", nullable = false)
    private String cause;

    @Column(name = "report_result", nullable = false)
    private String reportResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id")
    private College college;

    @Builder(builderMethodName = "createReport")
    public Report(LocalDateTime reportDate, String carNumber, String cause, String reportResult) {
        this.reportDate = reportDate;
        this.carNumber = carNumber;
        this.cause = cause;
        this.reportResult = reportResult;

    }

}
