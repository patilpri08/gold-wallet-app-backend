package com.goldrental.repository;

import com.goldrental.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByUser_Id(Long user_id);

    List<Rental> findByJewelleryItem_Id(Long id);

    /**
     * Sum of securityBlocked (or securityDeposit) for active rentals of a user.
     * Adjust the field name to match your Rental entity (securityBlocked / securityDeposit).
     */
    @Query("SELECT COALESCE(SUM(r.securityBlocked), 0) FROM Rental r WHERE r.user.id = :userId AND r.rentalStatus = 'ACTIVE'")
    int sumBlockedForActiveRentalsByUserId(@Param("userId") Long userId);
}
