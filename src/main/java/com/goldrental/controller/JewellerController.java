package com.goldrental.controller;

import com.goldrental.dto.JewelleryInventoryResponse;
import com.goldrental.dto.JewelleryItemRequest;
import com.goldrental.service.JewellerService;
import com.goldrental.service.JewelleryInventoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jewellers")
public class JewellerController {

    private final JewellerService jewellerService;
    private final JewelleryInventoryService jewelleryInventoryService;

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
    public ResponseEntity<?> getInventory(@PathVariable Long id) {
        try {
            List<JewelleryInventoryResponse> items = jewellerService.getInventory(id);
            return ResponseEntity.ok(items);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage()); // "No jewelry items found for user id: 3"
        }
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

    @PostMapping
    public ResponseEntity<Map<String, String>> createItem(
            @RequestPart("item") JewelleryItemRequest item,
            @RequestPart("file") MultipartFile file) {

        boolean success = jewelleryInventoryService.createItem(item, file);

        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "Jewellery item saved successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Failed to save jewellery item");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}
