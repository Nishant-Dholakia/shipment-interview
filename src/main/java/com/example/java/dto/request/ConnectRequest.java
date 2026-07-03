package com.example.java.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ConnectRequest {

    @NotNull(message = "toUserId is required")
    private Long toUserId;
}
