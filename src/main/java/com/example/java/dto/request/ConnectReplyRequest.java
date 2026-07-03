package com.example.java.dto.request;


import com.example.java.entity.ConnectionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ConnectReplyRequest {

    @NotNull(message = "connectionId and action are required")
    private Long connectionId;

    @NotBlank(message = "connectionId and action are required")
    private String action;
}
