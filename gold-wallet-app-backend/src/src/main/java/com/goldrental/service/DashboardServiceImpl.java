package com.goldrental.service;

import com.goldrental.entity.Rental;
import com.goldrental.repository.*;
import com.goldrental.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final JewellerRepository jewellerRepository;
    private final RentalRepository rentalRepository;
    private final WalletRepository walletRepository;

    @Override
    public Map<String, Object> getStats() {
        Map<String, Object> map = new HashMap<>();
        map.put("totalUsers", userRepository.count());
        map.put("totalJewellers", jewellerRepository.count());
        map.put("totalRentals", rentalRepository.count());
        map.put("walletAccounts", walletRepository.count());
        return map;
    }

    @Override
    public Map<String, Object> getRevenueDetails() {
        Map<String, Object> map = new HashMap<>();
        map.put("rentalRevenue",
                rentalRepository.findAll().stream()
                        .map(Rental::getTotal_rent)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        return map;
    }

    @Override
    public Map<String, Object> getRentalStats() {
        Map<String, Object> map = new HashMap<>();
        map.put("activeRentals", rentalRepository.findAll().stream()
                .filter(r -> r.getRentalStatus().equals("ACTIVE"))
                .count());

        map.put("returnedRentals", rentalRepository.findAll().stream()
                .filter(r -> r.getRentalStatus().equals("RETURNED"))
                .count());

        return map;
    }
}
