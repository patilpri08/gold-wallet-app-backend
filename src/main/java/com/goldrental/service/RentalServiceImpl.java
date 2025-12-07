package com.goldrental.service;

import com.goldrental.dto.RentalDto;
import com.goldrental.dto.RentalRequest;
import com.goldrental.entity.JewelleryItem;
import com.goldrental.entity.Rental;
import com.goldrental.entity.Wallet;
import com.goldrental.exception.JewelleryNotFoundException;
import com.goldrental.repository.JewelleryItemRepository;
import com.goldrental.repository.RentalRepository;
import com.goldrental.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final JewelleryItemRepository jewelleryItemRepository;
    private final RentalRepository rentalRepository;
    private final WalletRepository walletRepository;

    @Override
    public RentalDto rentJewellery(RentalRequest request) {

        JewelleryItem item = jewelleryItemRepository
                .findById(request.getJewelleryId())
                .orElseThrow(() -> new RuntimeException("Jewellery item not found"));

        Wallet wallet = walletRepository.findByUser_Id(request.getCustomerId());
        if (wallet == null) {
            throw new RuntimeException("Wallet not found for user");
        }

        BigDecimal rate = item.getDailyRentalRate() != null
                ? item.getDailyRentalRate()
                : BigDecimal.ZERO;

        LocalDate start = request.getRentalStartDate();
        LocalDate end = request.getRentalEndDate();

        long diffDays = ChronoUnit.DAYS.between(start, end);
        BigDecimal rentalAmount = rate.multiply(BigDecimal.valueOf(diffDays));

        if (wallet.getBalance().compareTo(rentalAmount) < 0) {
            throw new RuntimeException("Insufficient wallet balance");
        }

        wallet.setBalance(wallet.getBalance().subtract(rentalAmount));
        walletRepository.save(wallet);

        Rental rental = new Rental();
        rental.setUser_id(request.getCustomerId());   // ✅ link to user
        rental.setId(item.getId());             // ✅ link to jewellery
        rental.setStartDate(start);
        rental.setEndDate(end);
        rental.setTotalRent(rentalAmount);
        rental.setRentalStatus("ACTIVE");

        rentalRepository.save(rental);

        return mapToDto(rental, item);
    }

    @Override
    public RentalDto returnJewellery(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental Id not found"));

        rental.setRentalStatus("RETURNED");
        rentalRepository.save(rental);

        JewelleryItem item = jewelleryItemRepository
                .findById(rental.getJewelleryId())
                .orElseThrow(() -> new RuntimeException("Jewellery item not found"));

        return mapToDto(rental, item);
    }

    @Override
    public List<RentalDto> getUserRentals(Long user_id) {
        return rentalRepository.findByUser_Id(user_id)
                .stream()
                .map(r -> mapToDto(
                        r,
                        jewelleryItemRepository.findById(r.getJewelleryId())
                                .orElseThrow(() -> new JewelleryNotFoundException(r.getJewelleryId()))
                ))
                .collect(Collectors.toList());
    }

    private RentalDto mapToDto(Rental rental, JewelleryItem item) {
        RentalDto dto = new RentalDto();
        dto.setId(rental.getId());
        dto.setUser_id(rental.getUser_id());          // ✅ correct userId
        dto.setJewelleryId(rental.getJewelleryId());
        dto.setStartDate(rental.getStartDate());
        dto.setEndDate(rental.getEndDate());
        dto.setRentalStatus(rental.getTotalRent());
        dto.setTotalRent(rental.getRentalStatus());
        return dto;
    }
}