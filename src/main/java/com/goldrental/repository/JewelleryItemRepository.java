package com.goldrental.repository;

import com.goldrental.entity.JewelleryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JewelleryItemRepository extends JpaRepository<JewelleryItem, Long> {

    // If one Jeweller can have multiple JewelleryItems:
    List<JewelleryItem> findByJeweller_Id(Long jewellerId);

    // If you expect only one item per Jeweller:
    Optional<JewelleryItem> findFirstByJeweller_Id(Long jewellerId);
}