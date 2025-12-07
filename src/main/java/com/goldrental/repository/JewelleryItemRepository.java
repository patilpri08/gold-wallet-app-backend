package com.goldrental.repository;

import com.goldrental.entity.JewelleryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JewelleryItemRepository extends JpaRepository<JewelleryItem, Long> {
    // You can add custom queries if needed
}