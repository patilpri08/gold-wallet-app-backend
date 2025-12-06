package main.java.com.goldrental.controller;

import main.java.com.goldrental.dto.InventoryRequest;
import main.java.com.goldrental.entity.Jeweller;
import main.java.com.goldrental.service.JewellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Jeweller jeweller) {
        return ResponseEntity.ok(jewellerService.registerJeweller(jeweller));
    }

    @PostMapping("/{id}/verify")
    public ResponseEntity<?> verify(@PathVariable Long id) {
        return ResponseEntity.ok(jewellerService.verifyJeweller(id));
    }

    // ---- Inventory ----
    @GetMapping("/{id}/inventory")
    public ResponseEntity<?> getInventory(@PathVariable Long id) {
        return ResponseEntity.ok(jewellerService.getInventory(id));
    }

    @PostMapping("/{id}/inventory")
    public ResponseEntity<?> addItem(@PathVariable Long id, @RequestBody InventoryRequest request) {
        return ResponseEntity.ok(jewellerService.addInventoryItem(id, request));
    }

    @PutMapping("/inventory/{itemId}")
    public ResponseEntity<?> updateItem(@PathVariable Long itemId, @RequestBody InventoryRequest request) {
        return ResponseEntity.ok(jewellerService.updateInventoryItem(itemId, request));
    }

    @DeleteMapping("/inventory/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable Long itemId) {
        jewellerService.deleteInventoryItem(itemId);
        return ResponseEntity.ok("Item removed");
    }
}
