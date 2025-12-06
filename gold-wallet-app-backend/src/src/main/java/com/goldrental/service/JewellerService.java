package com.goldrental.service;

import com.goldrental.dto.InventoryRequest;
import com.goldrental.entity.Jeweller;

public interface JewellerService {

    Jeweller registerJeweller(Jeweller jeweller);

    Jeweller verifyJeweller(Long id);

    Object getAllJewellers();

    Object getJewellerById(Long id);

    Object getInventory(Long jewellerId);

    Object addInventoryItem(Long jewellerId, InventoryRequest request);

    Object updateInventoryItem(Long itemId, InventoryRequest request);

    void deleteInventoryItem(Long itemId);
}
