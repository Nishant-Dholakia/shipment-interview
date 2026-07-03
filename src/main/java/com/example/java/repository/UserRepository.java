package com.example.java.repository;


import com.example.java.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {


    boolean existsByEmail(@NotBlank String email);
}
