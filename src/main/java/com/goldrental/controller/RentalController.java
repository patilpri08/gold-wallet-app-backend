package com.goldrental.controller;

import com.goldrental.dto.RentalDto;
import com.goldrental.dto.RentalRequest;
import com.goldrental.dto.RentalResponse;
import com.goldrental.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rental")
public class RentalController {

    private final RentalService rentalService;

    @PostMapping("/book")
    public ResponseEntity<Map<String, Object>> rentJewellery(@RequestBody RentalRequest request) {
        try {
            boolean booked = rentalService.rentJewellery(request);

            if (booked) {
                return ResponseEntity.ok(Map.of(
                        "status", HttpStatus.OK.value(),
                        "message", "Jewellery booked successfully"
                ));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "message", "Jewellery could not be booked"
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "message", "Error while booking jewellery: " + e.getMessage()
            ));
        }
    }

    @PostMapping("/return/{rentalId}")
    public ResponseEntity<?> returnJewellery(@PathVariable Long rentalId) {
        return ResponseEntity.ok(rentalService.returnJewellery(rentalId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RentalResponse>> userRentals(@PathVariable Long userId) {
        return ResponseEntity.ok(rentalService.getUserRentals(userId));
    }
    // Confirm rental by ID
    @PutMapping("/{id}/confirm")
    public ResponseEntity<Boolean> confirmRental(@PathVariable Long id) {
        Boolean confirmedRental = rentalService.confirmRental(id);
        return ResponseEntity.ok(confirmedRental);
    }

}
