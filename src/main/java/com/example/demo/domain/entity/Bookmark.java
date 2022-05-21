// 05-14 일단 제외시키기로 결정
//package com.example.demo.domain.entity;
//
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Entity
//@Table(name = "Bookmark")
//@Getter
//public class Bookmark {
//
//    @Column(name = "book_id")
//    @Id
//    @GeneratedValue
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "collage_id", nullable = false)
//    private Collage collage;
//
//    @Builder(builderMethodName = "createCharge")
//    public Bookmark(Long id, User user, Collage collage) {
//        this.id = id;
//        this.user = user;
//        this.collage = collage;
//    }
//
//}
