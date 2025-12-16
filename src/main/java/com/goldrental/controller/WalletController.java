package com.goldrental.controller;

import com.goldrental.dto.TransactionResponse;
import com.goldrental.dto.WalletRequest;
import com.goldrental.dto.WalletResponse;
import com.goldrental.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/{userId}")
    public ResponseEntity<WalletResponse> getSummary(@PathVariable Long userId) {
        try {
            WalletResponse walletResponse = walletService.getSummary(userId);
            return ResponseEntity.ok(walletResponse);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
        }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addMoney(@RequestBody WalletRequest request) {
        try {
            return ResponseEntity.ok(walletService.addMoney(request));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody WalletRequest request) {
        try {
            Map<String, Object> result = walletService.withdrawAmount(request);

            // If service indicates failure, return 400 or 404 depending on context
            if (Boolean.FALSE.equals(result.get("success"))) {
                return ResponseEntity.badRequest().body(result);
            }

            return ResponseEntity.ok(result);

        } catch (IllegalArgumentException ex) {
            // Handle validation errors
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "success", false,
                            "message", ex.getMessage()
                    ));

        } catch (Exception ex) {
            // Log the exception in real code
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "message", "Failed to withdraw money: " + ex.getMessage()
                    ));
        }
    }

    @GetMapping("/transactions")
    public ResponseEntity<?> getTransactions(
            @RequestParam Long userId,
            @RequestParam(required = false, defaultValue = "ALL") String filter) {
        try {
            List<TransactionResponse> txns = walletService.getTransactions(userId, filter);

            if (txns == null || txns.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                                "success", false,
                                "message", "No transactions found for userId=" + userId
                        ));
            }

            return ResponseEntity.ok(txns);

        } catch (IllegalArgumentException ex) {
            // Handle bad input
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "success", false,
                            "message", ex.getMessage()
                    ));

        } catch (Exception ex) {
            // Log the exception in real code
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "message", "Failed to fetch transactions: " + ex.getMessage()
                    ));
        }
    }

}
