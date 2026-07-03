package com.example.java.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ConnectRequestResponse {

    private Long id;

    private Long fromUserId;
    private Long toUserId;
}
