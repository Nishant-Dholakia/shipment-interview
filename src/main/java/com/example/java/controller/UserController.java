package com.example.java.controller;


import com.example.java.dto.request.ConnectReplyRequest;
import com.example.java.dto.request.ConnectRequest;
import com.example.java.dto.request.UserCreateRequest;
import com.example.java.dto.response.ConnectRequestResponse;
import com.example.java.dto.response.UserCreateResponse;
import com.example.java.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserCreateResponse> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userCreateRequest));
    }

    @PostMapping("/connect")
    public ResponseEntity<ConnectRequestResponse> connectRequest(
            @RequestHeader("current_user_id") Long current_user_id,
            @RequestBody ConnectRequest connectRequest
    ){
        return ResponseEntity.ok(userService.connectRequest(current_user_id, connectRequest));
    }

    @PatchMapping("/connect/respond")
    public ResponseEntity<?> connectRespond(
            @RequestHeader("current_user_id") Long current_user_id,
            @Valid @RequestBody ConnectReplyRequest connectReplyRequest
    ){

        return ResponseEntity.ok(userService.connectResponse(current_user_id, connectReplyRequest));
    }
}
