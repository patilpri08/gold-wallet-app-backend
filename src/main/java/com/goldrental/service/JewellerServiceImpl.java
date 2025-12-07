package com.goldrental.service;

import com.goldrental.dto.InventoryRequest;
import com.goldrental.entity.Jeweller;
import com.goldrental.entity.JewelleryItem;
import com.goldrental.repository.JewellerRepository;
import com.goldrental.repository.JewelleryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JewellerServiceImpl implements JewellerService {

    private final JewellerRepository jewellerRepository;
    private final JewelleryItemRepository jewelleryItemRepository;

    @Override
    public Jeweller registerJeweller(Jeweller jeweller) {
        jeweller.setVerified(false);
        return jewellerRepository.save(jeweller);
    }

    @Override
    public Jeweller verifyJeweller(Long id) {
        Jeweller jeweller = jewellerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jeweller not found"));

        jeweller.setVerified(true);
        return jewellerRepository.save(jeweller);
    }

    @Override
    public Object getAllJewellers() {
        return jewellerRepository.findAll();
    }

    @Override
    public Object getJewellerById(Long id) {
        return jewellerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jeweller not found"));

    }

    @Override
    public Object getInventory(Long id) {
        return jewelleryItemRepository.findById(id);
    }

    @Override
    public Object addInventoryItem(Long id, InventoryRequest request) {
        Jeweller jeweller = jewellerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jeweller not found"));


        JewelleryItem item = new JewelleryItem();
        item.setJeweller(jeweller);
        item.setName(request.getName());
        item.setType(request.getType());
        item.setWeight(request.getWeight());
        item.setDailyRentalRate(request.getDailyRentalRate());

        return jewelleryItemRepository.save(item);
    }

    @Override
    public Object updateInventoryItem(Long itemId, InventoryRequest request) {
        JewelleryItem item = jewelleryItemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Jeweller not found"));

        item.setName(request.getName());
        item.setType(request.getType());
        item.setWeight(request.getWeight());
        item.setDailyRentalRate(request.getDailyRentalRate());

        return jewelleryItemRepository.save(item);
    }

    @Override
    public void deleteInventoryItem(Long itemId) {
        jewelleryItemRepository.deleteById(itemId);
    }
}
