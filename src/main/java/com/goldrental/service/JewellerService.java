package com.goldrental.service;

import com.goldrental.dto.JewelleryInventoryResponse;
import com.goldrental.dto.JewelleryItemRequest;
import com.goldrental.dto.RegisterJweller;
import com.goldrental.entity.Jeweller;
import com.goldrental.entity.JewelleryItem;

import java.util.List;
import java.util.Optional;

public interface JewellerService {

    Jeweller registerJeweller(RegisterJweller jeweller);

    Jeweller verifyJeweller(Long id);

    Object getAllJewellers();

    Object getJewellerById(Long id);

    List<JewelleryInventoryResponse> getInventory(Long jewellerId);

    Object addInventoryItem(Long jewellerId, JewelleryItemRequest request);

    Object updateInventoryItem(Long itemId, JewelleryItemRequest request);

    void deleteInventoryItem(Long itemId);

}

