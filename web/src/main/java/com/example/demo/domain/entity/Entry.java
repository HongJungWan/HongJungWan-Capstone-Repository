package com.example.demo.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "entry")
@Getter
public class Entry {

    @Column(name = "timeEntry")
    @Id
    @GeneratedValue
    private Long timeEntry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder(builderMethodName = "createCharge")
    public Entry(Long timeEntry, User user) {
        this.timeEntry = timeEntry;
        this.user = user;
    }

}
