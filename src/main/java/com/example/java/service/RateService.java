package com.example.java.service;


import com.example.java.dto.request.RateCreateRequest;
import com.example.java.dto.request.RateSearchRequest;
import com.example.java.dto.response.RateCreateResponse;
import com.example.java.dto.response.RateSearchResponse;
import com.example.java.entity.Rate;
import com.example.java.entity.RateType;
import com.example.java.entity.User;
import com.example.java.exception.BadRequestException;
import com.example.java.repository.RateRepository;
import com.example.java.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateService {

    private final RateRepository rateRepository;
    private final UserRepository userRepository;

    public @Nullable RateCreateResponse createRate(Long currentUserId, @Valid RateCreateRequest request) {

        if(!(request.getType().equals("BUY") || request.getType().equals("SELL"))) {
            throw new BadRequestException("type must be either BUY or SELL");
        }
        User user = userRepository.findById(currentUserId.intValue())
                .orElseThrow(() -> new BadRequestException("User not found"));

        Rate rate = Rate.builder()
                .price(request.getPrice())
                .type(request.getType().equals("BUY") ? RateType.BUY : RateType.SELL)
                .fromLocation(request.getFromLocation())
                .toLocation(request.getToLocation())
                .currency(request.getCurrency())
                .validTo(request.getValidTo())
                .validFrom(request.getValidFrom())
                .transitDays(request.getTransitDays())
                .owner(user)
                .build();

        rateRepository.save(rate);

        return new RateCreateResponse();
    }

//    public RateSearchResponse searchRate(Long currentUserId, @Valid RateSearchRequest rateSearchRequest) {
//
////        List<Rate> ownerRates =
//
//        return new RateSearchResponse();
//    }
}
