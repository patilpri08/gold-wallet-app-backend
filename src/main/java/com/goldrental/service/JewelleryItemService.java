package com.goldrental.service;

import com.goldrental.dto.JewelleryItemRequest;
import com.goldrental.entity.JewelleryItem;
import com.goldrental.repository.JewelleryItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class JewelleryItemService {

    private final JewelleryItemRepository jewelleryItemRepository;

    public JewelleryItemService(JewelleryItemRepository jewelleryItemRepository) {
        this.jewelleryItemRepository = jewelleryItemRepository;
    }

    public List<JewelleryItem> getAllItems() {
        return jewelleryItemRepository.findAll();
    }

    public JewelleryItem getItemById(Long id) {
        return jewelleryItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jewellery item not found"));
    }

    public JewelleryItem createItem(JewelleryItemRequest request, MultipartFile file) {
        try {
            // 1. Ensure upload directory exists
            String uploadDir = "c:/uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 2. Save file to disk
            String fileName = System.currentTimeMillis() + "_" +  file.getOriginalFilename();
            File savedFile = new File(uploadDir + fileName);
            file.transferTo(savedFile);

            // 3. Map DTO to Entity
            JewelleryItem item = new JewelleryItem();
            item.setName(request.getName());
            item.setType(request.getType());
            item.setJewelleryCategory(request.getJewelleryCategory());
            item.setPrice(request.getPrice());
            item.setWeight(request.getWeight());
            item.setPurity(request.getPurity());
            item.setRentPerDay(request.getDailyRent());
            item.setDailyRentalRate(request.getDailyRentalRate());
            item.setRentPerDay(request.getRentPerDay());
            item.setRentPerWeekMonth(request.getRentPerWeekMonth());
            item.setReplacementValue(request.getReplacementValue());
            item.setSecurityDeposit(request.getSecurityDeposit());
            item.setAvailability(request.getAvailability());
            item.setJewellery_condition(request.getCondition());

            // 4. Set file path in entity
            item.setPhotos(savedFile.getAbsolutePath());

            // 5. Save entity
            return jewelleryItemRepository.save(item);

        } catch (IOException e) {
            System.err.println("File upload failed: " + e.getMessage());
            throw new RuntimeException("File upload failed", e);
        } catch (Exception e) {
            System.err.println("Error creating jewellery item: " + e.getMessage());
            throw new RuntimeException("Error creating jewellery item", e);
        }
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

        return jewelleryItemRepository.save(existing);
    }

    public void deleteItem(Long id) {
        jewelleryItemRepository.deleteById(id);
    }
}