package com.example.java.dto.request;

import com.example.java.entity.RateType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class RateCreateRequest {


    @NotBlank
    private String type;

    @NotBlank
    private String fromLocation;
    @NotBlank
    private String toLocation;
    @Positive(message = "price must be greater than 0")
    private double price;


    private LocalDate validFrom;
    private LocalDate validTo;

    @NotBlank
    private String currency;
    @Positive(message = "transitDays must be greater than 0")
    private Long transitDays;
}
