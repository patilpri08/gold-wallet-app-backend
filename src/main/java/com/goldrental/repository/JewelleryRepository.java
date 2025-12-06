package main.java.com.goldrental.repository;

import main.java.com.goldrental.entity.JewelleryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JewelleryRepository extends JpaRepository<JewelleryItem, Long> {

    List<JewelleryItem> findByJewellerId(Long jewellerId);
}
