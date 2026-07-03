package com.example.java.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class RateSearchRequest {

    private LocalDate date;
    @NotBlank(message = "fromLocation and toLocation are both required")
    private String toLocation;
    @NotBlank(message = "fromLocation and toLocation are both required")
    private String fromLocation;
}
