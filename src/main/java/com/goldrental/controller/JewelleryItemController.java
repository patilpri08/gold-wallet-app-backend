package com.goldrental.controller;

import com.goldrental.entity.JewelleryItem;
import com.goldrental.service.JewelleryItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jewellery-items")
public class JewelleryItemController {

    private final JewelleryItemService service;

    public JewelleryItemController(JewelleryItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<JewelleryItem> getAllItems() {
        return service.getAllItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JewelleryItem> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getItemById(id));
    }

    @PostMapping
    public ResponseEntity<JewelleryItem> createItem(@RequestBody JewelleryItem item) {
        return ResponseEntity.ok(service.createItem(item));
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