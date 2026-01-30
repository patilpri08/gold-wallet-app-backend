package com.goldrental.service;

import com.goldrental.dto.JewelleryInventoryResponse;
import com.goldrental.dto.JewelleryItemRequest;
import com.goldrental.entity.Jeweller;
import com.goldrental.entity.JewelleryInventory;
import com.goldrental.repository.JewellerRepository;
import com.goldrental.repository.JewelleryInventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class JewelleryInventoryService {

    private static final Logger log = LoggerFactory.getLogger(JewelleryInventoryService.class);

    private final JewelleryInventoryRepository jewelleryInventoryRepository;
    private final JewellerRepository jewellerRepository;

    public JewelleryInventoryService(JewelleryInventoryRepository jewelleryInventoryRepository,
                                     JewellerRepository jewellerRepository) {
        this.jewelleryInventoryRepository = jewelleryInventoryRepository;
        this.jewellerRepository = jewellerRepository;
    }

    public List<JewelleryInventoryResponse> getAllItems() {
        return jewelleryInventoryRepository.findAll()
                .stream()
                .map(JewelleryInventoryResponse::new)
                .toList();
    }

    public JewelleryInventory getItemById(Long id) {
        return jewelleryInventoryRepository.findById(id)
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
            JewelleryInventory item = new JewelleryInventory();
            item.setCategory(request.getCategory());
            item.setWeight(request.getWeight());
            item.setPurity(request.getPurity());
            item.setReplacementValue(request.getReplacementValue());
            item.setBlockAmount(request.getBlockAmount());
            item.setAvailableFrom(request.getAvailableFrom());
            item.setJewellery_condition(request.getCondition());
            item.setConditionNotes(request.getConditionNotes());
            item.setDescription(request.getDescription());
            item.setHallmark(request.getHallmark());
            item.setInsuranceCovered(request.getInsuranceCovered());
            item.setInsuranceProvider(request.getInsuranceProvider());
            item.setListingStatus(request.getListingStatus());
            item.setOccasion(request.getOccasion());
            item.setResurface(request.getResurface());
            item.setStatus(request.getStatus());
            item.setTypes(request.getTypes());
            item.setValuationAmount(request.getValuationAmount());
            item.setValuationDate(request.getValuationDate());
            item.setDailyRental(request.getDailyRental());

            // 4. Set jeweller relationship
            Long jewellerId = request.getJewellerId();
            Jeweller jeweller = jewellerRepository.findByJewellerUser_Id(jewellerId)
                    .orElseThrow(() -> new RuntimeException("Jeweller not found for userId: " + jewellerId));
            item.setJeweller(jeweller);

            // 5. Set file path
            item.setFrontViewUrl(fileName);

            // 6. Save entity
            JewelleryInventory saved = jewelleryInventoryRepository.save(item);
            return saved.getId() != null;

        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }
    }

    public JewelleryInventory updateItem(Long id, JewelleryInventory updatedItem) {
        JewelleryInventory existing = getItemById(id);

        existing.setCategory(updatedItem.getCategory());
        existing.setWeight(updatedItem.getWeight());
        existing.setPurity(updatedItem.getPurity());
        existing.setReplacementValue(updatedItem.getReplacementValue());
        existing.setBlockAmount(updatedItem.getBlockAmount());
        existing.setAvailableFrom(updatedItem.getAvailableFrom());
        existing.setJewellery_condition(updatedItem.getJewellery_condition());
        existing.setConditionNotes(updatedItem.getConditionNotes());
        existing.setDescription(updatedItem.getDescription());
        existing.setHallmark(updatedItem.getHallmark());
        existing.setInsuranceCovered(updatedItem.getInsuranceCovered());
        existing.setInsuranceProvider(updatedItem.getInsuranceProvider());
        existing.setListingStatus(updatedItem.getListingStatus());
        existing.setOccasion(updatedItem.getOccasion());
        existing.setResurface(updatedItem.getResurface());
        existing.setStatus(updatedItem.getStatus());
        existing.setTypes(updatedItem.getTypes());
        existing.setValuationAmount(updatedItem.getValuationAmount());
        existing.setValuationDate(updatedItem.getValuationDate());
        existing.setDailyRental(updatedItem.getDailyRental());
        // Relationship
        existing.setJeweller(updatedItem.getJeweller());
        existing.setJewelleryItemUser(updatedItem.getJewelleryItemUser());

        existing.setFrontViewUrl(updatedItem.getFrontViewUrl());

        return jewelleryInventoryRepository.save(existing);
    }

    public void deleteItem(Long id) {
        jewelleryInventoryRepository.deleteById(id);
    }

    public List<JewelleryInventoryResponse> getItemByCategory(String categoryName) {
        return jewelleryInventoryRepository.findByCategory(categoryName)
                .stream()
                .map(JewelleryInventoryResponse::new)
                .toList();
    }
}