package com.goldrental.service;

import com.goldrental.dto.RentalDto;
import com.goldrental.dto.RentalRequest;
import com.goldrental.dto.RentalResponse;
import com.goldrental.entity.*;
import com.goldrental.exception.JewelleryNotFoundException;
import com.goldrental.exception.RentalNotFoundException;
import com.goldrental.exception.UserNotFoundException;
import com.goldrental.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final JewelleryInventoryRepository jewelleryItemRepository;
    private final RentalRepository rentalRepository;
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    @Transactional
    public Boolean rentJewellery(RentalRequest request) {

        JewelleryInventory jewelleryInventory = jewelleryItemRepository
                .findById(request.getJewelleryId())
                .orElseThrow(() -> new RuntimeException("Jewellery item not found"));

        if ("RENTED".equalsIgnoreCase(jewelleryInventory.getListingStatus())){
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

        List<Rental> rentals = rentalRepository.findByUser_Id(request.getUserId());

        boolean hasActiveRental = rentals.stream()
                .anyMatch(r -> r.getRentalStatus().equals("ACTIVE"));

        if (hasActiveRental) {
            throw new RuntimeException("Item is already rented by you");
        }

        if (rental.getUser() != null && rental.getUser().getId().equals(request.getUserId())) {
            throw new RuntimeException("Item is already rented by you");
        }
        BigDecimal deposit = jewelleryInventory.getBlockAmount();
        rental.setSecurityBlocked(deposit); // record PaymentTransaction type BLOCK for deposit

        // ✅ link to walletUser
        // ✅ link to jewellery
        jewelleryInventory.setListingStatus(request.getRentalStatus());
        jewelleryInventory.setJewelleryItemUser(user);
        rental.setJewelleryItem(jewelleryInventory);
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
                        JewelleryInventory jewelleryInventory = jewelleryItemRepository.findById(r.getJewelleryItem().getId())
                                .orElseThrow(() -> new JewelleryNotFoundException(r.getJewelleryItem().getId()));
                        return mapToDto(jewelleryInventory,userId);
                    })
                    .collect(Collectors.toList());

        } else if ("JEWELLER".equalsIgnoreCase(user.getRole())) {
            // Jeweller: rentals of items owned by this jeweller
            List<String> availabilityList = Arrays.asList("REQUESTED", "RENTED", "COMPLETED", "CONFIRMED");

            return jewelleryItemRepository.findByJewellerIdAndListingStatusIn(user.getJeweller().getId(), availabilityList)
                    .stream()
                    .filter(item -> item.getRental() != null) // ✅ check rentals exist
                    .map(item1 -> mapToDto(item1, userId))
                    .collect(Collectors.toList());

        } else {
            throw new IllegalArgumentException("Unsupported role: " + user.getRole());
        }
    }

    private RentalResponse mapToDto(JewelleryInventory item, Long userId) {
        //System.out.println("====================="+item.getId());
        RentalResponse rentalResponse = new RentalResponse();
        rentalResponse.setId(item.getRental().getId());       // ✅ correct userId
        rentalResponse.setJewelleryId(item.getRental().getJewelleryItem().getId());
        rentalResponse.setStartDate(item.getRental().getStartDate());
        rentalResponse.setEndDate(item.getRental().getEndDate());;
        rentalResponse.setJewellerName(item.getJeweller().getBusinessName());
        rentalResponse.setJewelleryCategory(item.getCategory());
        // ✅ Correct mapping
        rentalResponse.setRentalStatus(item.getListingStatus());   // status field
        rentalResponse.setTotalRent(item.getRental().getTotalRent());         // rent amount
        rentalResponse.setUserId(userId);
        return rentalResponse;
    }

    public Boolean confirmRental(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RentalNotFoundException(id));

        JewelleryInventory item = rental.getJewelleryItem();

        if (!"CONFIRMED".equalsIgnoreCase(item.getListingStatus())) {
            item.setListingStatus("CONFIRMED");
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

        JewelleryInventory jewelleryInventory = rental.getJewelleryItem();

        // Ensure only the user who rented it can cancel
        if (!rental.getUser().getId().equals(requestingUserId)) {
            throw new RuntimeException("You cannot cancel a rental you did not make");
        }

        // ✅ Check for dues remaining before allowing cancellation
        if (calculateDuesRemaining(rental) != null && calculateDuesRemaining(rental).compareTo(BigDecimal.ZERO) > 0) {
            throw new RuntimeException("Cannot cancel rental. Outstanding dues remain: " + calculateDuesRemaining(rental));
        }
        BigDecimal blocked = Optional.ofNullable(rental.getSecurityBlocked()).orElse(BigDecimal.ZERO);
        BigDecimal dues = calculateDuesRemaining(rental);
        BigDecimal deducted = dues.min(blocked);
        rental.setSecurityBlocked(blocked.subtract(deducted));

// record transactions for CHARGE_FROM_BLOCK and REFUND as needed
        // Update availability back to AVAILABLE
        jewelleryInventory.setListingStatus("AVAILABLE");
        jewelleryItemRepository.save(jewelleryInventory);

        // Mark rental as cancelled (instead of deleting)
        rental.setRentalStatus("CANCELLED");
        rentalRepository.save(rental);

        return true;
    }

    public BigDecimal calculateDuesRemaining(Rental rental) {
        long days = ChronoUnit.DAYS.between(rental.getStartDate(), rental.getEndDate());

        // Use blockAmount or a dedicated dailyRentalRate field
        BigDecimal dailyRate = rental.getJewelleryItem().getBlockAmount() != null
                ? rental.getJewelleryItem().getBlockAmount()
                : BigDecimal.ZERO;

        BigDecimal baseCost = dailyRate.multiply(BigDecimal.valueOf(days));

        BigDecimal penalties = rental.getPenalties() != null ? rental.getPenalties() : BigDecimal.ZERO;
        BigDecimal payments = rental.getPaymentsMade() != null ? rental.getPaymentsMade() : BigDecimal.ZERO;

        BigDecimal totalCost = baseCost.add(penalties);
        BigDecimal duesRemaining = totalCost.subtract(payments);

        return duesRemaining.max(BigDecimal.ZERO); // never negative
    }

}