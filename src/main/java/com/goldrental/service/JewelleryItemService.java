package com.goldrental.service;

import com.goldrental.dto.JewelleryInventoryResponse;
import com.goldrental.dto.JewelleryItemRequest;
import com.goldrental.entity.JewelleryItem;
import com.goldrental.repository.JewellerRepository;
import com.goldrental.repository.JewelleryItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class JewelleryItemService {

    private final JewelleryItemRepository jewelleryItemRepository;
    private final JewellerRepository jewellerRepository;

    public JewelleryItemService(JewelleryItemRepository jewelleryItemRepository, JewellerRepository jewellerRepository) {
        this.jewelleryItemRepository = jewelleryItemRepository;
        this.jewellerRepository = jewellerRepository;
    }

    public List<JewelleryInventoryResponse> getAllItems() {
        List<JewelleryItem> items = jewelleryItemRepository.findAll();

        return items.stream()
                .map(JewelleryInventoryResponse::new) // uses your DTO constructor
                .toList();
    }

    public JewelleryItem getItemById(Long id) {
        return jewelleryItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jewellery item not found"));
    }


    public Boolean createItem(JewelleryItemRequest request, MultipartFile file) {
        try {
            // 1. Ensure upload directory exists
            String uploadDir = "c:/uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 2. Save file to disk
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File savedFile = new File(uploadDir + fileName);
            file.transferTo(savedFile);
            // 3. Map DTO to Entity
            JewelleryItem item = new JewelleryItem();
            item.setType(request.getType());
            item.setJewelleryCategory(request.getJewelleryCategory());
            item.setPrice(request.getPrice());
            item.setWeight(request.getWeight());
            item.setPurity(request.getPurity());
            item.setRentPerDay(request.getDailyRent());
            item.setRentPerDay(request.getRentPerDay());
            item.setRentPerWeekMonth(request.getRentPerWeekMonth());
            item.setReplacementValue(request.getReplacementValue());
            item.setSecurityDeposit(request.getSecurityDeposit());
            item.setAvailability(request.getAvailability());
            item.setJewellery_condition(request.getJewellery_condition());
            // 4. Set jeweller relationship
            Long jewellerId = Long.valueOf(request.getJeweller_id());
            System.out.println(jewellerId);
            item.setJeweller(jewellerRepository.findByJewellerUser_Id(jewellerId)
                    .orElseThrow(() -> new RuntimeException("Jeweller not found")));

            // 5. Set file path
            item.setPhotos(savedFile.getPath());

            // 6. Save entity
            JewelleryItem saved =  jewelleryItemRepository.save(item);

            return saved.getId() != null;

        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }
    }

    public JewelleryItem updateItem(Long id, JewelleryItem updatedItem) {
        JewelleryItem existing = getItemById(id);

        existing.setType(updatedItem.getType());
        existing.setPrice(updatedItem.getPrice());
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