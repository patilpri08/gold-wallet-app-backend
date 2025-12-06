package com.goldrental.service;

import com.goldrental.dto.InventoryRequest;
import com.goldrental.entity.Jeweller;
import com.goldrental.entity.JewelleryItem;
import com.goldrental.repository.JewellerRepository;
import com.goldrental.repository.JewelleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JewellerServiceImpl implements JewellerService {

    private final JewellerRepository jewellerRepository;
    private final JewelleryRepository jewelleryRepository;

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
    public Object getInventory(Long jewellerId) {
        return jewelleryRepository.findByJewellerId(jewellerId);
    }

    @Override
    public Object addInventoryItem(Long jewellerId, InventoryRequest request) {
        Jeweller jeweller = jewellerRepository.findById(jewellerId)
                .orElseThrow(() -> new RuntimeException("Jeweller not found"));


        JewelleryItem item = new JewelleryItem();
        item.setJeweller(jeweller);
        item.setName(request);
        item.setType(request.getType());
        item.setWeight(request.getWeight());
        item.setDailyRentalRate(request.getDailyRentalRate());

        return jewelleryRepository.save(item);
    }

    @Override
    public Object updateInventoryItem(Long itemId, InventoryRequest request) {
        JewelleryItem item = jewelleryRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Jeweller not found"));

        item.setName(request.getName());
        item.setType(request.getType());
        item.setWeight(request.getWeight());
        item.setDailyRentalRate(request.getDailyRentalRate());

        return jewelleryRepository.save(item);
    }

    @Override
    public void deleteInventoryItem(Long itemId) {
        jewelleryRepository.deleteById(itemId);
    }
}
