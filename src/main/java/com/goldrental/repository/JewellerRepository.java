package com.goldrental.repository;

import com.goldrental.entity.Jeweller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JewellerRepository extends JpaRepository<Jeweller, Long> {

    // find jeweller by linked user id
    Optional<Jeweller> findByJewellerUser_Id(Long userId);

}