package com.goldrental.repository;

import com.goldrental.entity.Jeweller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JewellerRepository extends JpaRepository<Jeweller, Long> {
}
