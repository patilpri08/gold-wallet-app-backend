package com.goldrental.repository;

import com.goldrental.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

//    List<Rental> findByUserId(Long user_id);

    List<Rental> findByJewellerId(Long jewellerId);
}
