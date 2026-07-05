package com.example.java.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class RateSearchResult {
    private String fromLocation;
    private String toLocation;
    private String via;
    private Double price;
    private String currency;
    private Long transitDays;
}
