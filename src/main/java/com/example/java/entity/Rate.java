package com.example.java.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Rate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private RateType type;

    private String fromLocation;
    private String toLocation;
    private double price;

    private LocalDate validFrom;
    private LocalDate validTo;

    private String currency;

    private Long transitDays;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private User owner;
}
