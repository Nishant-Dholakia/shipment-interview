package com.example.java.dto.response;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateResponse {

    private Long id;
    private String name;

    private String email;
}
