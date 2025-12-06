package com.goldrental.repository;

import com.goldrental.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findByUserId(Long user_id);
}
