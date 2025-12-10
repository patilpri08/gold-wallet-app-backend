package com.goldrental.service;

import com.goldrental.dto.RentalDto;
import com.goldrental.dto.RentalRequest;
import com.goldrental.entity.*;
import com.goldrental.exception.JewelleryNotFoundException;
import com.goldrental.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final JewelleryItemRepository jewelleryItemRepository;
    private final RentalRepository rentalRepository;
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    @Override
    public Boolean rentJewellery(RentalRequest request) {

        JewelleryItem jewelleryItem = jewelleryItemRepository
                .findById(request.getJewelleryId())
                .orElseThrow(() -> new RuntimeException("Jewellery item not found"));

        if (jewelleryItem.getAvailability().equals("RENTED")) {
            throw new RuntimeException("Wallet not found for walletUser");
        }

        Wallet wallet = walletRepository.findByWalletUser_Id(request.getUserId());
        if (wallet == null) {
            throw new RuntimeException("Wallet not found for walletUser");
        }

        if (wallet.getBalance().compareTo(request.getRentalAmount()) < 0) {
            throw new RuntimeException("Insufficient wallet balance");
        }

        wallet.setBalance(wallet.getBalance().subtract(request.getRentalAmount()));
        walletRepository.save(wallet);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Rental rental = new Rental();
        // ✅ link to walletUser
        // ✅ link to jewellery
        jewelleryItem.setAvailability(request.getRentalStatus());
        rental.setJewelleryItem(jewelleryItem);
        rental.setStartDate(request.getRentalStartDate());
        rental.setEndDate(request.getRentalEndDate());
        rental.setTotalRent(request.getRentalAmount());
        rental.setRentalStatus(request.getRentalStatus());
//        rental.setUser(user);
        // ✅ set current timestamp
        rental.setCreatedDate(LocalDateTime.now());
        rental.setUser(user);
        rentalRepository.save(rental);

        // ✅ Return boolean instead of DTO
        return true;
    }

    @Override
    public RentalDto returnJewellery(Long rentalId) {
/*        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental Id not found"));

        rental.setRentalStatus("RETURNED");
        rentalRepository.save(rental);

//        JewelleryItem item = jewelleryItemRepository
//                .findById(rental.getJewelleryId())
//                .orElseThrow(() -> new RuntimeException("Jewellery item not found"));

        return mapToDto(rental, item);*/
        return null;
    }

    @Override
    public List<RentalDto> getUserRentals(Long userId) {
        return rentalRepository.findByUser_Id(userId)
                .stream()
                .map(r -> {
                    Long jewelleryId = r.getJewelleryItem() != null ? r.getJewelleryItem().getId() : null;
                    assert jewelleryId != null;
                    JewelleryItem jewelleryItem = jewelleryItemRepository.findById(jewelleryId)
                            .orElseThrow(() -> new JewelleryNotFoundException(jewelleryId));
                    return mapToDto(r, jewelleryItem);
                })
                .collect(Collectors.toList());
    }

    private RentalDto mapToDto(Rental rental, JewelleryItem item) {
        RentalDto dto = new RentalDto();
        dto.setId(rental.getId());
        dto.setUser_id(rental.getUser().getId());          // ✅ correct userId
        dto.setJewelleryId(rental.getJewelleryItem().getId());
        dto.setStartDate(rental.getStartDate());
        dto.setEndDate(rental.getEndDate());

        // ✅ Correct mapping
        dto.setRentalStatus(rental.getRentalStatus());   // status field
        dto.setTotalRent(rental.getTotalRent());         // rent amount

        return dto;
    }
}