package main.java.com.goldrental.repository;

import main.java.com.goldrental.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByUserId(Long userId);

    List<Rental> findByJewellerId(Long jewellerId);
}
