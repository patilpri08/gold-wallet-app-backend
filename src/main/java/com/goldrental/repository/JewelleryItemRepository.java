package com.goldrental.repository;

import com.goldrental.dto.RentalResponse;
import com.goldrental.entity.JewelleryItem;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface JewelleryItemRepository extends JpaRepository<JewelleryItem, Long> {

    // If one Jeweller can have multiple JewelleryItems:
    List<JewelleryItem> findByJeweller_Id(Long jewellerId);
    // If you expect only one item per Jeweller:
    Optional<JewelleryItem> findFirstByJeweller_Id(Long jewellerId);
    List<JewelleryItem> findByJeweller_JewellerUser_Id(Long userId);

    // Filter by jewellerId and multiple availability values
    List<JewelleryItem> findByJewellerIdAndAvailabilityIn(Long jewellerId, List<String> availability);
}
