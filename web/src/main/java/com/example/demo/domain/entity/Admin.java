/*
*  매핑 완료
* */

package com.example.demo.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "admin")
@Getter
public class Admin {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String password;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"admin"})
    private List<Collage> collages = new ArrayList<>();

    @Builder(builderMethodName = "createAdmin")
    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }

}