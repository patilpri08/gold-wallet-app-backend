package main.java.com.goldrental.repository;

import main.java.com.goldrental.entity.Jeweller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JewellerRepository extends JpaRepository<Jeweller, Long> {
}
