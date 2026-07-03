package com.example.java.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;
}
