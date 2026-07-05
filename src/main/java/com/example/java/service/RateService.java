package com.example.java.service;


import com.example.java.dto.request.RateCreateRequest;
import com.example.java.dto.request.RateSearchRequest;
import com.example.java.dto.response.RateCreateResponse;
import com.example.java.dto.response.RateSearchResponse;
import com.example.java.dto.response.RateSearchResult;
import com.example.java.entity.Rate;
import com.example.java.entity.RateType;
import com.example.java.entity.User;
import com.example.java.exception.BadRequestException;
import com.example.java.repository.ConnectionRepository;
import com.example.java.repository.RateRepository;
import com.example.java.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import javax.naming.directory.SearchResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RateService {

    private final RateRepository rateRepository;
    private final UserRepository userRepository;
    private final ConnectionRepository connectionRepository;

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

    public RateSearchResponse searchRate(Long currentUserId, @Valid RateSearchRequest rateSearchRequest) {
        System.out.println("Entered search rate");
        User owner = userRepository.findById(currentUserId.intValue())
                .orElseThrow(()-> new BadRequestException("User not found"));

        List<User> connections = connectionRepository.findAllByFromUserOrToUser(owner);
        System.out.println("connections size: " + connections.size());
        if(connections.isEmpty()){
            return null;
        }

        List<RateSearchResult> results = new ArrayList<>();


        List<Rate> fromRates = rateRepository
        .findByFromLocationAndValidFromLessThanEqualAndValidToGreaterThanEqual(rateSearchRequest.getFromLocation(), rateSearchRequest.getDate(), rateSearchRequest.getDate());
        System.out.println("fromRates size: " + fromRates.size());
        if(fromRates.isEmpty()){
            throw new InternalError("Something went wrong, please try again");
        }

        List<Rate> toRates = rateRepository
                .findByToLocationAndValidFromLessThanEqualAndValidToGreaterThanEqual(rateSearchRequest.getToLocation(), rateSearchRequest.getDate(), rateSearchRequest.getDate());
        System.out.println("toRates size: " + toRates.size());
        if(toRates.isEmpty()){
            throw new InternalError("Something went wrong, please try again");
        }

        results.addAll(getDirectRoute(owner, connections, fromRates, toRates));

        System.out.println("results size: " + results.size());
        results.addAll(getViaRoute(owner, connections, toRates, fromRates));
        System.out.println("results size: " + results.size());
        results.forEach(System.out::println);

        RateSearchResponse rateSearchResponse = RateSearchResponse.builder().results(results).build();
        System.out.println(rateSearchResponse);
        return rateSearchResponse;
    }

    private List<RateSearchResult> getViaRoute(User owner, List<User> connections, List<Rate> toRates, List<Rate> fromRates) {
        List<RateSearchResult> results = new ArrayList<>();

        fromRates.forEach(fromRate -> {
            for(Rate toRate : toRates) {
                if(fromRate.getToLocation().equals(toRate.getFromLocation()) // via exists
                && fromRate.getCurrency().equals(toRate.getCurrency()) // both have same currency
                && isValidRate(owner, connections, fromRate) // both have valid owners and their rate types
                && isValidRate(owner, connections, toRate)){
                    results.add(
                            RateSearchResult.builder()
                                    .via(fromRate.getToLocation())
                                    .price(fromRate.getPrice() + toRate.getPrice())
                                    .currency(fromRate.getCurrency())
                                    .fromLocation(fromRate.getFromLocation())
                                    .toLocation(fromRate.getToLocation())
                                    .transitDays(fromRate.getTransitDays() +  toRate.getTransitDays())
                                    .build()
                    );
                }
            }
        });
        return results;
    }

    private List<RateSearchResult> getDirectRoute
            (User owner, List<User> connections,List<Rate> fromRates, List<Rate> toRates) {
        List<RateSearchResult> results = new ArrayList<>();

        fromRates.forEach(fromRate -> {
            if(toRates.contains(fromRate)) {
                if(isValidRate(owner, connections, fromRate)){
                    results.add(
                            RateSearchResult.builder()
                                    .price(fromRate.getPrice())
                                    .currency(fromRate.getCurrency())
                                    .fromLocation(fromRate.getFromLocation())
                                    .toLocation(fromRate.getToLocation())
                                    .transitDays(fromRate.getTransitDays())
                                    .build()
                    );
                }
            }
        });
        return results;
    }

    public boolean isValidRate(User user, List<User> connections, Rate rate ) {
        return (
                (rate.getOwner().equals(user) && rate.getType().equals(RateType.BUY))
                ||
                (connections.contains(rate.getOwner()) && rate.getType().equals(RateType.SELL))
        );
    }
}
