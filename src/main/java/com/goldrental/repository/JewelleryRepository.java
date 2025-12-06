package com.goldrental.repository;

import com.goldrental.entity.JewelleryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JewelleryRepository extends JpaRepository<JewelleryItem, Long> {

    List<JewelleryItem> findByJewellerId(Long id);

    Optional<JewelleryItem> findById(Long id);

}
