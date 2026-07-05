package com.example.java.dto.response;


import lombok.Builder;
import lombok.Getter;

import java.security.Timestamp;
import java.time.LocalDateTime;

@Builder
@Getter
public class ConnectReplyResponseAccept extends ConnectReplyResponse{
    private Long id;
    private Long fromUserId;
    private Long toUserId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime acceptedAt;
}
