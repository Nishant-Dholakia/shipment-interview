package com.example.java.repository;

import com.example.java.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

}
