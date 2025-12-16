package com.goldrental.repository;

import com.goldrental.dto.TransactionResponse;
import com.goldrental.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {

    /**
     * Sum of amount for wallet transactions for a user.
     * Positive amounts are credits, negative are debits.
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) " +
            "FROM PaymentTransaction t " +
            "WHERE t.paymentTransactionUser.id = :userId")
    BigDecimal sumBalanceByUserId(@Param("userId") Long userId);

    /**
     * Return all transactions for a user as TransactionResponse DTOs.
     */
    @Query("SELECT new com.goldrental.dto.TransactionResponse(" +
            "t.id, t.txnDate, t.type, t.jewelleryName, t.duration, t.amount, t.mode) " +
            "FROM PaymentTransaction t " +
            "WHERE t.paymentTransactionUser.id = :userId " +
            "ORDER BY t.txnDate DESC")
    List<TransactionResponse> findAllByUserId(@Param("userId") Long userId);

    boolean existsByReferenceId(String reference);

    @Query("SELECT new com.goldrental.dto.TransactionResponse(" +
            "t.id, t.txnDate, t.type, t.jewelleryName, t.duration, t.amount, t.mode, t.status) " +
            "FROM PaymentTransaction t " +
            "WHERE t.paymentTransactionUser.id = :userId " +
            "AND t.type IN :types " +
            "ORDER BY t.txnDate DESC")
    List<TransactionResponse> findByPaymentTransactionUserIdAndType(
            @Param("userId") Long userId,
            @Param("types") List<String> types);

}