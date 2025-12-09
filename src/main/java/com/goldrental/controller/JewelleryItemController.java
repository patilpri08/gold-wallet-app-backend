package com.goldrental.controller;

import com.goldrental.dto.JewelleryInventoryResponse;
import com.goldrental.dto.JewelleryItemRequest;
import com.goldrental.entity.JewelleryItem;
import com.goldrental.service.JewelleryItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jewellery-items")
public class JewelleryItemController {

    private final JewelleryItemService service;

    public JewelleryItemController(JewelleryItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<JewelleryInventoryResponse> getAllItems() {
        return service.getAllItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JewelleryItem> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getItemById(id));
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createItem(
            @RequestPart("item") JewelleryItemRequest item,
            @RequestPart("file") MultipartFile file) {

        boolean success = service.createItem(item, file);

        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "Jewellery item saved successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Failed to save jewellery item");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<JewelleryItem> updateItem(@PathVariable Long id, @RequestBody JewelleryItem item) {
        return ResponseEntity.ok(service.updateItem(id, item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        service.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}