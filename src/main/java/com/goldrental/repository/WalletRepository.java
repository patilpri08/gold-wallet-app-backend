package main.java.com.goldrental.repository;

import main.java.com.goldrental.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findByUserId(Long userId);
}
