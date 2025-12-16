package com.goldrental.repository;

import com.goldrental.entity.Wallet;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findByWalletUser_Id(Long user_id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select w from Wallet w join fetch w.walletUser u where u.id = :userId")
    Optional<Wallet> findByWalletUser_IdForUpdate(@Param("userId") Long userId);

}
