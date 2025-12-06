package com.goldrental.controller;


import com.goldrental.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        return ResponseEntity.ok(dashboardService.getStats());
    }

    @GetMapping("/revenue")
    public ResponseEntity<?> getRevenue() {
        return ResponseEntity.ok(dashboardService.getRevenueDetails());
    }

    @GetMapping("/rentals")
    public ResponseEntity<?> getRentalStats() {
        return ResponseEntity.ok(dashboardService.getRentalStats());
    }
}
