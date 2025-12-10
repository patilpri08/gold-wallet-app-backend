package com.goldrental.controller;

import com.goldrental.dto.RentalRequest;
import com.goldrental.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rental")
public class RentalController {

    private final RentalService rentalService;

    @PostMapping("/book")
    public ResponseEntity<String> rentJewellery(@RequestBody RentalRequest request) {
        boolean booked = rentalService.rentJewellery(request);

        if (booked) {
            return ResponseEntity.ok("Jewellery booked successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Jewellery could not be booked");
        }
    }

    @PostMapping("/return/{rentalId}")
    public ResponseEntity<?> returnJewellery(@PathVariable Long rentalId) {
        return ResponseEntity.ok(rentalService.returnJewellery(rentalId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> userRentals(@PathVariable Long userId) {
        return ResponseEntity.ok(rentalService.getUserRentals(userId));
    }
}
