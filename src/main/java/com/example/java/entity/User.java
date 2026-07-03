package com.example.java.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.aot.generate.GeneratedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Email(message = "email format invalid")
    private String email;

    @OneToMany
    private List<Connection> connections;

    @OneToMany(mappedBy = "owner")
    private List<Rate> rates;

}
