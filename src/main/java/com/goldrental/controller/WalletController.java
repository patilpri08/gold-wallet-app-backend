package com.goldrental.controller;

import com.goldrental.dto.TransactionDto;
import com.goldrental.dto.WalletRequest;
import com.goldrental.dto.WalletResponse;
import com.goldrental.service.WalletService;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok(walletService.addMoney(request));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Map<String, Object>> withdraw(@RequestBody WalletRequest request) {
        return ResponseEntity.ok(walletService.withdrawAmount(request));
    }

    // com.goldrental.controller.WalletController
    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDto>> getTransactions(
            @RequestParam Long userId,
            @RequestParam(required = false, defaultValue = "ALL") String filter) {
        List<TransactionDto> txns = walletService.getTransactions(userId, filter);
        return ResponseEntity.ok(txns);
    }

}
