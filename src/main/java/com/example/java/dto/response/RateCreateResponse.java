package com.example.java.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RateCreateResponse {
    private String success = "true";
    private String message = "Rate created successfully";
}
