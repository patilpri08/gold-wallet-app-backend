package com.goldrental.service;

import com.goldrental.entity.JewelleryItem;
import com.goldrental.repository.JewelleryItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JewelleryItemService {

    private final JewelleryItemRepository repository;

    public JewelleryItemService(JewelleryItemRepository repository) {
        this.repository = repository;
    }

    public List<JewelleryItem> getAllItems() {
        return repository.findAll();
    }

    public JewelleryItem getItemById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jewellery item not found"));
    }

    public JewelleryItem createItem(JewelleryItem item) {
        return repository.save(item);
    }

    public JewelleryItem updateItem(Long id, JewelleryItem updatedItem) {
        JewelleryItem existing = getItemById(id);

        // Basic fields
        existing.setName(updatedItem.getName());
        existing.setType(updatedItem.getType());
        existing.setPrice(updatedItem.getPrice());
        existing.setDailyRentalRate(updatedItem.getDailyRentalRate());
        existing.setWeight(updatedItem.getWeight());

        // Relationship
        existing.setJeweller(updatedItem.getJeweller());

        // New fields
        existing.setAvailability(updatedItem.getAvailability());
        existing.setJewellery_condition(updatedItem.getJewellery_condition());
        existing.setJewelleryCategory(updatedItem.getJewelleryCategory());
        existing.setPhotos(updatedItem.getPhotos());
        existing.setPurity(updatedItem.getPurity());

        existing.setRentPerDay(updatedItem.getRentPerDay());
        existing.setRentPerWeekMonth(updatedItem.getRentPerWeekMonth());
        existing.setReplacementValue(updatedItem.getReplacementValue());
        existing.setSecurityDeposit(updatedItem.getSecurityDeposit());

        return repository.save(existing);
    }
    public void deleteItem(Long id) {
        repository.deleteById(id);
    }
}