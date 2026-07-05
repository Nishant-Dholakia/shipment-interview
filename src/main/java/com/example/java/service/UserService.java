package com.example.java.service;


import com.example.java.dto.request.ConnectReplyRequest;
import com.example.java.dto.request.ConnectRequest;
import com.example.java.dto.request.UserCreateRequest;
import com.example.java.dto.response.*;
import com.example.java.entity.Connection;
import com.example.java.entity.ConnectionStatus;
import com.example.java.entity.User;
import com.example.java.exception.BadRequestException;
import com.example.java.exception.ResourceNotFoundException;
import com.example.java.exception.UserUnauthorizedException;
import com.example.java.repository.ConnectionRepository;
import com.example.java.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ConnectionRepository connectionRepository;

    public UserCreateResponse createUser(@Valid UserCreateRequest userCreateRequest){

        if(userCreateRequest.getName().isEmpty() || userCreateRequest.getEmail().isEmpty()){
            throw new BadRequestException("name and email are required");
        }

        if(userRepository.existsByEmail(userCreateRequest.getEmail())){
            throw new BadRequestException("A user with this email already exists");
        }

        User user = new User();
        user.setName(userCreateRequest.getName());
        user.setEmail(userCreateRequest.getEmail());
        userRepository.save(user);

        UserCreateResponse userCreateResponse = UserCreateResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .id(user.getId())
                .build();

        return userCreateResponse;
    }

    public @Nullable ConnectRequestResponse connectRequest(Long userId, ConnectRequest connectRequest) {

        if(Objects.equals(userId, connectRequest.getToUserId())){
            throw new BadRequestException("A user cannot connect with themselves" );
        }

        System.out.println("ConnectRequest");
        User fromUser = userRepository.findById(userId.intValue())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
        User touser = userRepository.findById(connectRequest.getToUserId().intValue())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + connectRequest.getToUserId() + " not found"));

        if(connectionRepository.existsByFromUserAndToUserAndStatusNot(fromUser, touser, ConnectionStatus.REJECTED)){
            throw new BadRequestException("These users are already connected");

        }

        Connection connection = new Connection();
        connection.setStatus(ConnectionStatus.PENDING);
        connection.setFromUser(fromUser);
        connection.setToUser(touser);


        connectionRepository.save(connection);

        ConnectRequestResponse connectRequestResponse =
                ConnectRequestResponse.builder()
                        .fromUserId(userId)
                        .toUserId(connectRequest.getToUserId())
                        .id(connection.getId())
                        .build();

        return connectRequestResponse;
    }

    public @Nullable ConnectReplyResponse connectResponse(Long currentUserId, ConnectReplyRequest connectReplyRequest) {
        System.out.println(connectReplyRequest.getAction());
        if(!(Objects.equals(connectReplyRequest.getAction(), "ACCEPT") || Objects.equals(connectReplyRequest.getAction(), "REJECT"))) {
            throw new  BadRequestException("action must be either ACCEPT or REJECT");
        }

        Connection connection = connectionRepository.findById(connectReplyRequest.getConnectionId())
                .orElseThrow(() -> new ResourceNotFoundException("No connection request found with id " + connectReplyRequest.getConnectionId()));

        if(!connection.getStatus().equals(ConnectionStatus.PENDING)) {
            throw new BadRequestException("Only pending connection requests can be responded to");
        }

        if(! connection.getToUser().getId().equals(currentUserId)) {
            throw new BadRequestException("Only the receiver of the connection request can respond to it");
        }

        if(Objects.equals(connectReplyRequest.getAction(), "ACCEPT")){
            System.out.println("accepted invite");
            connection.setStatus(ConnectionStatus.ACCEPTED);
            connectionRepository.save(connection);
            return ConnectReplyResponseAccept.builder()
                    .acceptedAt(LocalDateTime.now())
                    .fromUserId(connection.getFromUser().getId())
                    .id(connection.getId())
                    .toUserId(currentUserId)
                    .status("ACCEPTED")
                    .build();
        }
        else if(Objects.equals(connectReplyRequest.getAction(), "REJECT")){
            connection.setStatus(ConnectionStatus.REJECTED);
            connectionRepository.save(connection);
            return ConnectReplyResponseReject.builder()
                    .rejectedAt(LocalDateTime.now())
                    .fromUserId(connection.getFromUser().getId())
                    .id(connection.getId())
                    .toUserId(currentUserId)
                    .status("REJECTED")
                    .build();
        }
        return null;
    }
}
