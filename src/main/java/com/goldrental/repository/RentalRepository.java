package com.goldrental.repository;

import com.goldrental.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByUser_Id(Long user_id);

    List<Rental> findByJewelleryItem_Id(Long id);


}
