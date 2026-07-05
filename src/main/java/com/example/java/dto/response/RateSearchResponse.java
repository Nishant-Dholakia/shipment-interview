package com.example.java.dto.response;


import lombok.*;

import java.util.List;


@Builder
@AllArgsConstructor
@ToString
@Getter
public class RateSearchResponse {
    private List<RateSearchResult> results;
}
