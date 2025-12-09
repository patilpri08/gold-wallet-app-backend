package com.goldrental.controller;

import com.goldrental.dto.JewelleryInventoryResponse;
import com.goldrental.dto.JewelleryItemRequest;
import com.goldrental.entity.JewelleryItem;
import com.goldrental.service.JewellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jewellers")
public class JewellerController {

    private final JewellerService jewellerService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(jewellerService.getAllJewellers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJeweller(@PathVariable Long id) {
        return ResponseEntity.ok(jewellerService.getJewellerById(id));
    }

    @PostMapping("/{id}/verify")
    public ResponseEntity<?> verify(@PathVariable Long id) {
        return ResponseEntity.ok(jewellerService.verifyJeweller(id));
    }

    @GetMapping("/{id}/inventory")
    public ResponseEntity<List<JewelleryInventoryResponse>> getInventory(@PathVariable Long id) {
        return ResponseEntity.ok(jewellerService.getInventory(id));
    }

    @PostMapping("/{id}/inventory")
    public ResponseEntity<String> addInventoryItem(@PathVariable Long id,
                                                   @RequestBody JewelleryItemRequest request) {
        boolean success = jewellerService.addInventoryItem(id, request);
        if (success) {
            return ResponseEntity.ok("Jewellery item saved successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save jewellery item");
        }
    }

    @PutMapping("/inventory/{itemId}")
    public ResponseEntity<?> updateItem(@PathVariable Long itemId, @RequestBody JewelleryItemRequest request) {
        return ResponseEntity.ok(jewellerService.updateInventoryItem(itemId, request));
    }

    @DeleteMapping("/inventory/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable Long itemId) {
        jewellerService.deleteInventoryItem(itemId);
        return ResponseEntity.ok("Item removed");
    }
}
