package com.goldrental.service;

import com.goldrental.dto.RentalDto;
import com.goldrental.dto.RentalRequest;
import com.goldrental.dto.RentalResponse;
import com.goldrental.entity.*;
import com.goldrental.exception.JewelleryNotFoundException;
import com.goldrental.exception.RentalNotFoundException;
import com.goldrental.exception.UserNotFoundException;
import com.goldrental.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
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

        if ("RENTED".equalsIgnoreCase(jewelleryItem.getAvailability())){
            throw new RuntimeException("Item is already rented by another user");
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
        if(!rental.getUser().getId().equals(request.getUserId())){
            throw new RuntimeException("Item is already rented by you");
        }
        // ✅ link to walletUser
        // ✅ link to jewellery
        jewelleryItem.setAvailability(request.getRentalStatus());
        jewelleryItem.setJewelleryItemUser(user);
        rental.setJewelleryItem(jewelleryItem);
        rental.setStartDate(request.getRentalStartDate());
        rental.setEndDate(request.getRentalEndDate());
        rental.setTotalRent(request.getRentalAmount());
        rental.setRentalStatus(request.getRentalStatus());
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
    public List<RentalResponse> getUserRentals(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if ("CUSTOMER".equalsIgnoreCase(user.getRole())) {
            // Normal user: rentals made by this user
            return rentalRepository.findByUser_Id(userId)
                    .stream()
                    .map(r -> {
                        JewelleryItem jewelleryItem = jewelleryItemRepository.findById(r.getJewelleryItem().getId())
                                .orElseThrow(() -> new JewelleryNotFoundException(r.getJewelleryItem().getId()));
                        return mapToDto(jewelleryItem,userId);
                    })
                    .collect(Collectors.toList());

        } else if ("JEWELLER".equalsIgnoreCase(user.getRole())) {
            // Jeweller: rentals of items owned by this jeweller
            List<String> availabilityList = Arrays.asList("REQUESTED", "RENTED", "COMPLETED");

            return jewelleryItemRepository.findByJewellerIdAndAvailabilityIn(user.getJeweller().getId(), availabilityList)
                    .stream()
                    .filter(item -> item.getRental() != null) // ✅ check rentals exist
                    .map(item1 -> mapToDto(item1, userId))
                    .collect(Collectors.toList());

        } else {
            throw new IllegalArgumentException("Unsupported role: " + user.getRole());
        }
    }

    private RentalResponse mapToDto(JewelleryItem item, Long userId) {
        //System.out.println("====================="+item.getId());
        RentalResponse rentalResponse = new RentalResponse();
        rentalResponse.setId(item.getRental().getId());       // ✅ correct userId
        rentalResponse.setJewelleryId(item.getRental().getJewelleryItem().getId());
        rentalResponse.setStartDate(item.getRental().getStartDate());
        rentalResponse.setEndDate(item.getRental().getEndDate());;
        rentalResponse.setJewellerName(item.getJeweller().getBusinessName());
        rentalResponse.setJewelleryCategory(item.getJewelleryCategory());
        // ✅ Correct mapping
        rentalResponse.setRentalStatus(item.getAvailability());   // status field
        rentalResponse.setTotalRent(item.getRental().getTotalRent());         // rent amount
        rentalResponse.setUserId(userId);
        return rentalResponse;
    }

    public Boolean confirmRental(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RentalNotFoundException(id));

        JewelleryItem item = rental.getJewelleryItem();

        if (!"CONFIRMED".equalsIgnoreCase(item.getAvailability())) {
            item.setAvailability("CONFIRMED");
            rental.setRentalStatus("CONFIRMED");
            jewelleryItemRepository.save(item);
            rentalRepository.save(rental);
            return true; // status changed
        }

        return false; // already confirmed, no change
    }

    public Boolean cancelRental(Long rentalId, Long requestingUserId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RentalNotFoundException(rentalId));

        JewelleryItem jewelleryItem = rental.getJewelleryItem();

        // Ensure only the user who rented it can cancel
        if (!rental.getUser().getId().equals(requestingUserId)) {
            throw new RuntimeException("You cannot cancel a rental you did not make");
        }

        // ✅ Check for dues remaining before allowing cancellation
        if (calculateDuesRemaining(rental) != null && calculateDuesRemaining(rental).compareTo(BigDecimal.ZERO) > 0) {
            throw new RuntimeException("Cannot cancel rental. Outstanding dues remain: " + calculateDuesRemaining(rental));
        }

        // Update availability back to AVAILABLE
        jewelleryItem.setAvailability("AVAILABLE");
        jewelleryItemRepository.save(jewelleryItem);

        // Mark rental as cancelled (instead of deleting)
        rental.setRentalStatus("CANCELLED");
        rentalRepository.save(rental);

        return true;
    }

    public BigDecimal calculateDuesRemaining(Rental rental) {
        long days = ChronoUnit.DAYS.between(rental.getStartDate(), rental.getEndDate());

        BigDecimal baseCost = rental.getJewelleryItem().getRentPerDay()
                .multiply(BigDecimal.valueOf(days));

//        BigDecimal totalCost = baseCost.add(
//                rental.getPenalties() != null ? rental.getPenalties() : BigDecimal.ZERO
//        );
//
//        BigDecimal payments = rental.getPaymentsMade() != null ? rental.getPaymentsMade() : BigDecimal.ZERO;

        return baseCost.max(BigDecimal.ZERO); // never negative
    }
}