package com.example.java.repository;

import com.example.java.entity.Connection;
import com.example.java.entity.ConnectionStatus;
import com.example.java.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {


    boolean existsByFromUserAndToUserAndStatus(User fromUser, User toUser, ConnectionStatus connectionStatus);
}
