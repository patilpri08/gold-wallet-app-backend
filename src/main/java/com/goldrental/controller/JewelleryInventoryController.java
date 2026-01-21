package com.goldrental.controller;

import com.goldrental.dto.JewelleryInventoryResponse;
import com.goldrental.entity.JewelleryInventory;
import com.goldrental.service.JewelleryInventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jewellery-items")
public class JewelleryInventoryController {

    private final JewelleryInventoryService service;

    public JewelleryInventoryController(JewelleryInventoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<JewelleryInventoryResponse> getAllItems() {
        return service.getAllItems();
    }

    @GetMapping("/category/{categoryName}")
    public List<JewelleryInventoryResponse> getItemByCategory(@PathVariable String categoryName){
        return service.getItemByCategory(categoryName);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JewelleryInventory> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getItemById(id));
    }


}