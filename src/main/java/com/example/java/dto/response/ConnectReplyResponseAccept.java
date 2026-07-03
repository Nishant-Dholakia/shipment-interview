package com.example.java.dto.response;


import lombok.Builder;

import java.security.Timestamp;
import java.time.LocalDateTime;

@Builder
public class ConnectReplyResponseAccept {
    private Long id;
    private Long fromUserId;
    private Long toUserId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime acceptedAt;
}
