package com.goldrental.repository;

import com.goldrental.entity.JewelleryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JewelleryRepository extends JpaRepository<JewelleryItem, Long> {

    List<JewelleryItem> findByJewellerId(Long jewellerId);

    List<JewelleryItem> findByJewelleryId(Long jewelleryId);

}
