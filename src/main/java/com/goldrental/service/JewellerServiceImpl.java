package com.goldrental.service;

import com.goldrental.dto.JewelleryInventoryResponse;
import com.goldrental.dto.JewelleryItemRequest;
import com.goldrental.dto.RegisterJweller;
import com.goldrental.entity.Jeweller;
import com.goldrental.entity.JewelleryItem;
import com.goldrental.entity.User;
import com.goldrental.entity.Wallet;
import com.goldrental.repository.JewellerRepository;
import com.goldrental.repository.JewelleryItemRepository;
import com.goldrental.repository.UserRepository;
import com.goldrental.repository.WalletRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JewellerServiceImpl implements JewellerService {

    private final JewellerRepository jewellerRepository;
    private final JewelleryItemRepository jewelleryItemRepository;
    private final PasswordEncoder passwordEncoder;
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public Jeweller registerJeweller(RegisterJweller registerJeweller) {
        // Create User
        User user = new User();
        user.setName(registerJeweller.getBusinessName()); // or getName() depending on your DTO
        user.setEmail(registerJeweller.getEmail());
        user.setPhone(registerJeweller.getPhone());
        user.setPassword(passwordEncoder.encode(registerJeweller.getPassword()));
        user.setRole("JEWELLER");

        userRepository.save(user);

        // Create Wallet for the new User
        Wallet wallet = new Wallet();
        wallet.setWalletUser(user);
        wallet.setBalance(BigDecimal.ZERO);
        walletRepository.save(wallet);

        // Create Jeweller entity
        Jeweller jeweller = new Jeweller();
        jeweller.setBusinessName(registerJeweller.getBusinessName());
        jeweller.setOwnerName(registerJeweller.getOwnerName());
        jeweller.setAddress(registerJeweller.getAddress());
        jeweller.setPhone(registerJeweller.getPhone());
        jeweller.setGstNumber(registerJeweller.getGstNumber());
        jeweller.setStoreDocs(registerJeweller.getStoreDocs());
        jeweller.setAccountNumber(registerJeweller.getAccountNumber());
        jeweller.setIfscCode(registerJeweller.getIfscCode());
        jeweller.setStaffEmail(registerJeweller.getStaffEmail());
        jeweller.setKycType(registerJeweller.getKycType());
        jeweller.setKycNumber(registerJeweller.getKycNumber());
        jeweller.setStoreTimings(registerJeweller.getStoreTimings());
        jeweller.setVerified(false);

        // Link User to Jeweller
        jeweller.setJewellerUser(user);

        // Save Jeweller (cascade will persist user if not already saved)
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
    public List<JewelleryInventoryResponse> getInventory(Long userId) {
        List<JewelleryItem> items = jewelleryItemRepository.findByJeweller_JewellerUser_Id(userId);

        if (items.isEmpty()) {
            throw new EntityNotFoundException("No jewellery items found");
        }

        return items.stream()
                .map(JewelleryInventoryResponse::new)
                .toList();
    }

    @Override
    public Boolean addInventoryItem(Long id, JewelleryItemRequest request) {
        Jeweller jeweller = jewellerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jeweller not found"));

        JewelleryItem item = new JewelleryItem();
        item.setJeweller(jeweller);
        item.setWeight(request.getWeight());

        JewelleryItem saved = jewelleryItemRepository.save(item);

        return saved.getId() != null;

    }

    @Override
    public Object updateInventoryItem(Long itemId, JewelleryItemRequest request) {
        JewelleryItem item = jewelleryItemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Jeweller not found"));

        item.setWeight(request.getWeight());

        return jewelleryItemRepository.save(item);
    }

    @Override
    public void deleteInventoryItem(Long itemId) {
        jewelleryItemRepository.deleteById(itemId);
    }
}
