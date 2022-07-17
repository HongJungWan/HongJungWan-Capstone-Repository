package com.example.demo.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.*;

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
    private DateTime reportDate;

    @Column(name = "car_number", nullable = false)
    private String carNumber;

    @Column(name = "cause", nullable = false)
    private String cause;

    @Column(name = "report_result", nullable = false)
    private String reportResult;
}
