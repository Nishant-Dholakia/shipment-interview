package com.example.java.controller;


import com.example.java.dto.request.RateCreateRequest;
import com.example.java.dto.request.RateSearchRequest;
import com.example.java.dto.response.RateCreateResponse;
import com.example.java.dto.response.RateSearchResponse;
import com.example.java.entity.Rate;
import com.example.java.service.RateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rates")
@RequiredArgsConstructor
public class RateController {


    private final RateService rateService;

    @PostMapping
    public ResponseEntity<RateCreateResponse> createRate(
            @RequestHeader(name="current_user_id") Long current_user_id,
            @Valid @RequestBody RateCreateRequest request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rateService.createRate(current_user_id, request));
    }

    @PostMapping("/search")
    public ResponseEntity<RateSearchResponse> searchRate(
            @RequestHeader(name="current_user_id") Long current_user_id,
            @Valid @RequestBody RateSearchRequest rateSearchRequest
            ){

        return ResponseEntity.ok(rateService.searchRate(current_user_id, rateSearchRequest));
    }
}
