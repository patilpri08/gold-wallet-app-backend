package com.goldrental.service;

import com.goldrental.dto.RentalDto;
import com.goldrental.dto.RentalRequest;
import com.goldrental.entity.JewelleryItem;
import com.goldrental.entity.Rental;
import com.goldrental.entity.Wallet;
import com.goldrental.repository.JewelleryRepository;
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

    private final JewelleryRepository jewelleryRepository;
    private final RentalRepository rentalRepository;
    private final WalletRepository walletRepository;

    @Override
    public RentalDto rentJewellery(RentalRequest request) {

        JewelleryItem item = jewelleryRepository
                .findById(request.getJewelleryId())
                .orElseThrow(() -> new RuntimeException("Jewellery item not found"));

        Wallet wallet = walletRepository.findByUserId(request.getCustomerId());
        if (wallet == null) {
            throw new RuntimeException("Wallet not found for user");
        }

        // Safe BigDecimal usage
        BigDecimal rate = item.getDailyRentalRate() != null
                ? item.getDailyRentalRate()
                : BigDecimal.ZERO;

        LocalDate start = request.getRentalStartDate();
        LocalDate end = request.getRentalEndDate();

        // Calculate difference in days
        long diffDays = ChronoUnit.DAYS.between(start, end);

        // Convert to BigDecimal
        BigDecimal days = BigDecimal.valueOf(diffDays);

        BigDecimal rentalAmount = rate.multiply(days);

        // Correct BigDecimal comparison: < 0 means insufficient balance
        if (wallet.getBalance().compareTo(rentalAmount) < 0) {
            throw new RuntimeException("Insufficient wallet balance");
        }

        // Deduct / Block the rental amount
        wallet.setBalance(wallet.getBalance().subtract(rentalAmount));
        walletRepository.save(wallet);

        Rental rental = new Rental();
        rental.setId(request.getJewelleryId());
        rental.setJewellery_id(item.getJeweller().getId());
        rental.setJeweller_id(item.getId());
        rental.setRentalStartDate(LocalDate.now());
        rental.setRentalEndDate(LocalDate.now().plusDays(request.getDays()));
        rental.setTotal_rent(rentalAmount);
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

        JewelleryItem item = jewelleryRepository
                .findById(rental.getJewellery_id())
                .orElseThrow(() -> new RuntimeException("Jewellery item not found"));

        return mapToDto(rental, item);
    }

    @Override
    public List<RentalDto> getUserRentals(Long userId) {
        return rentalRepository.findByUserId(userId)
                .stream()
                .map(r -> mapToDto(
                        r,
                        jewelleryRepository.findById(r.getJewellery_id())
                                .orElseThrow(() -> new RuntimeException("Jewellery item not found"))
                ))
                .collect(Collectors.toList());

    }

    private RentalDto mapToDto(Rental rental, JewelleryItem item) {
        RentalDto dto = new RentalDto();
        dto.setRentalId(rental.getId());
        dto.setUserId(rental.getId());
        dto.setJewellerId(rental.getJeweller_id());
        dto.setInventoryItemId(rental.getJewellery_id());
        dto.setItemName(item.getName());
        dto.setItemType(item.getType());
        dto.setWeight(item.getWeight());
        dto.setDailyRentalRate(item.getDailyRentalRate());
        dto.setStartDate(rental.getRentalStartDate());
        dto.setEndDate(rental.getRentalEndDate());
        dto.setTotalAmount(rental.getTotal_rent());
        dto.setStatus(rental.getRentalStatus());
        return dto;
    }
}
