// com.goldrental.repository.PaymentTransactionRepository
package com.goldrental.repository;

import com.goldrental.dto.TransactionDto;
import com.goldrental.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public interface PaymentTransactionRepository extends CrudRepository<PaymentTransaction, Long> {

    /**
     * Sum of amount for wallet transactions for a user.
     * Positive amounts are credits, negative are debits.
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM PaymentTransaction t WHERE t.jewelleryItemUser.id = :userId")
    int sumBalanceByUserId(@Param("userId") Long userId);


    @Query("SELECT new com.goldrental.dto.TransactionDto(t.txnId, t.txnDate, t.type, t.jewelleryName, t.duration, t.amount, t.mode) " +
            "FROM PaymentTransaction t WHERE t.jewelleryItemUser.id = :userId ORDER BY t.txnDate DESC")
    List<TransactionDto> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT new com.goldrental.dto.TransactionDto(t.txnId,  t.txnDate, t.type, t.jewelleryName, t.duration, t.amount, t.mode) " +
            "FROM PaymentTransaction t WHERE t.jewelleryItemUser.id = :userId AND t.type IN :types ORDER BY t.txnDate DESC")
    List<TransactionDto> findByUserIdAndTypes(@Param("userId") Long userId, @Param("types") List<String> types);
}
