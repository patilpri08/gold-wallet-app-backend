package main.java.com.goldrental.controller;

import main.java.com.goldrental.dto.RentalRequest;
import main.java.com.goldrental.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rental")
public class RentalController {

    private final RentalService rentalService;

    @PostMapping("/book")
    public ResponseEntity<?> rentJewellery(@RequestBody RentalRequest request) {
        return ResponseEntity.ok(rentalService.rentJewellery(request));
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
