package com.example.java.repository;

import com.example.java.entity.Connection;
import com.example.java.entity.ConnectionStatus;
import com.example.java.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {


    boolean existsByFromUserAndToUserAndStatus(User fromUser, User toUser, ConnectionStatus connectionStatus);

    Object findAllByFromUser(User owner);

    @Query(
            "select fromUser as user from Connection where toUser = :owner and status != 'REJECTED' union select toUser as user from Connection where fromUser = :owner and status != 'REJECTED'"
    )
    List<User> findAllByFromUserOrToUser(User owner);


    boolean existsByFromUserAndToUserAndStatusNot(User fromUser, User touser, ConnectionStatus connectionStatus);
}
