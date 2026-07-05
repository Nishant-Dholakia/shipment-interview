package com.example.java.repository;

import com.example.java.entity.Rate;
import com.example.java.entity.RateType;
import com.example.java.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    Rate findByOwnerAndType(User owner, RateType rateType);

    List<Rate> findByFromLocationAndValidFromLessThanEqualAndValidToGreaterThanEqual(@NotBlank(message = "fromLocation and toLocation are both required") String fromLocation, LocalDate date, LocalDate rateSearchRequestDate);

    List<Rate> findByToLocationAndValidFromLessThanEqualAndValidToGreaterThanEqual(@NotBlank(message = "fromLocation and toLocation are both required") String fromLocation, LocalDate date, LocalDate rateSearchRequestDate);
}
