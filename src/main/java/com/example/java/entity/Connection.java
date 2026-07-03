package com.example.java.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Connection extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(cascade = CascadeType.ALL)
    private User fromUser;

    @ManyToOne(cascade = CascadeType.ALL)
    private User toUser;


    @Enumerated(EnumType.STRING)
    private ConnectionStatus status = ConnectionStatus.PENDING;
}
