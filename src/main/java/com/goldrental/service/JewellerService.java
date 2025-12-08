package com.goldrental.service;

import com.goldrental.dto.JewelleryItemRequest;
import com.goldrental.dto.RegisterJweller;
import com.goldrental.entity.Jeweller;

public interface JewellerService {

    Jeweller registerJeweller(RegisterJweller jeweller);

    Jeweller verifyJeweller(Long id);

    Object getAllJewellers();

    Object getJewellerById(Long id);

    Object getInventory(Long jewellerId);

    Object addInventoryItem(Long jewellerId, JewelleryItemRequest request);

    Object updateInventoryItem(Long itemId, JewelleryItemRequest request);

    void deleteInventoryItem(Long itemId);

}

