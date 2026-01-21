package com.goldrental.repository;

import com.goldrental.entity.JewelleryInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JewelleryInventoryRepository extends JpaRepository<JewelleryInventory, Long> {

    // If one Jeweller can have multiple JewelleryItems:
    List<JewelleryInventory> findByJeweller_Id(Long jewellerId);
    // If you expect only one item per Jeweller:
    Optional<JewelleryInventory> findFirstByJeweller_Id(Long jewellerId);
    List<JewelleryInventory> findByJeweller_JewellerUser_Id(Long userId);

    // Filter by jewellerId and multiple availability values
    List<JewelleryInventory> findByJewellerIdAndListingStatusIn(Long jewellerId, List<String> availability);

    List<JewelleryInventory> findByCategory(String categoryName);

}
