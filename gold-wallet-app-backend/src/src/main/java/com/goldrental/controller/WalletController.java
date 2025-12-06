package com.goldrental.controller;

import com.goldrental.dto.WalletRequest;
import com.goldrental.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getWallet(@PathVariable Long userId) {
        return ResponseEntity.ok(walletService.getWallet(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMoney(@RequestBody WalletRequest request) {
        return ResponseEntity.ok(walletService.addMoney(request));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody WalletRequest request) {
        return ResponseEntity.ok(walletService.withdrawAmount(request));
    }

    @GetMapping("/transactions/{userId}")
    public ResponseEntity<?> getTransactions(@PathVariable Long userId) {
        return ResponseEntity.ok(walletService.getTransactions(userId));
    }
}
