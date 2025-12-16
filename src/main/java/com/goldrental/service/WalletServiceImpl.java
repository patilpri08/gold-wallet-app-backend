package com.goldrental.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;

import com.goldrental.dto.TransactionResponse;
import com.goldrental.dto.WalletDto;
import com.goldrental.dto.WalletRequest;
import com.goldrental.dto.WalletResponse;
import com.goldrental.entity.PaymentTransaction;
import com.goldrental.entity.WalletTransaction;
import com.goldrental.repository.WalletTransactionRepository;
import com.goldrental.entity.User;
import com.goldrental.entity.Wallet;
import com.goldrental.repository.PaymentTransactionRepository;
import com.goldrental.repository.RentalRepository;
import com.goldrental.repository.UserRepository;
import com.goldrental.repository.WalletRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final RentalRepository rentalRepo;
    private final WalletTransactionRepository walletTransactionRepository;

    @Override
    @Transactional(readOnly = true)
    public WalletResponse getSummary(Long userId) {
        try {
            BigDecimal walletBalance = Optional.ofNullable(paymentTransactionRepository.sumBalanceByUserId(userId)).orElse(BigDecimal.ZERO);
            BigDecimal blocked = Optional.ofNullable(rentalRepo.sumBlockedForActiveRentalsByUserId(userId)).orElse(BigDecimal.ZERO);
            BigDecimal available = walletBalance.subtract(blocked);
            if (available.compareTo(BigDecimal.ZERO) < 0) {
                available = BigDecimal.ZERO;
            }
            WalletResponse walletResponse = new WalletResponse();
            walletResponse.setAvailable(available);
            walletResponse.setBalance(walletBalance);
            walletResponse.setBlocked(blocked);

            return walletResponse;
        }catch(Exception e){
            e.printStackTrace();
            return new WalletResponse(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }
    }

    @Transactional
    public Map<String, Object> addMoney(WalletRequest request) {
        Map<String, Object> result = new LinkedHashMap<>();

        // Basic validation
        if (request == null || request.getUserId() == null) {
            result.put("success", false);
            result.put("message", "Request or userId cannot be null");
            return result;
        }
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            result.put("success", false);
            result.put("message", "Amount must be a positive value");
            return result;
        }

        Long userId = request.getUserId();
        try {
            // Obtain wallet with pessimistic lock; create if absent
            Wallet wallet = walletRepository.findByWalletUser_IdForUpdate(userId)
                    .orElseGet(() -> createWalletForUserWithLock(userId));

            // Idempotency: if reference provided and exists, return current state
            String reference = request.getReference_id();
            if (reference != null && !reference.isBlank() && paymentTransactionRepository.existsByReferenceId(reference)) {
                result.put("success", true);
                result.put("message", "Duplicate request: transaction already processed");
                result.put("wallet", mapToDto(wallet));
                return result;
            }

            // Update balance
            BigDecimal newBalance = Optional.ofNullable(wallet.getBalance()).orElse(BigDecimal.ZERO)
                    .add(request.getAmount());
            wallet.setBalance(newBalance);
            wallet = walletRepository.save(wallet);

            // Persist wallet transaction (ledger)
            WalletTransaction txn = buildTxn(wallet, request); // set type=CREDIT, amount, reference, balanceAfter = newBalance, createdAt etc.
            txn = walletTransactionRepository.save(txn);

            // Persist payment transaction linking to wallet transaction
            PaymentTransaction ptxn = buildPaymentTxn(txn, request); // set provider, providerTxnId, status, payload, amount, currency
            paymentTransactionRepository.save(ptxn);

            // Compute blocked amount if applicable (adapt return type if repo returns Integer/Long)
            BigDecimal blocked = Optional.ofNullable(rentalRepo.sumBlockedForActiveRentalsByUserId(userId))
                    .map(b -> {
                        return (BigDecimal) b;
                    })
                    .orElse(BigDecimal.ZERO);

            // Prepare response map
            result.put("success", true);
            result.put("message", "Amount added successfully");
            result.put("wallet", mapToDto(wallet)); // your DTO mapper
            result.put("transactionId", txn.getId());
            result.put("paymentTransactionId", ptxn.getId());
            result.put("blocked", blocked);
            return result;

        } catch (Exception ex) {
            // Log the exception (use logger in real code)
            ex.printStackTrace();
            result.put("success", false);
            result.put("message", "Failed to add money: " + ex.getMessage());
            return result;
        }
    }

    private WalletTransaction buildTxn(Wallet wallet, WalletRequest request) {
        WalletTransaction txn = new WalletTransaction();

        // Link to wallet
        txn.setWallet(wallet);

        // Transaction type: CREDIT for adding money
        txn.setType("CREDIT");

        // Amount from request
        txn.setAmount(request.getAmount());

        // Balance after this transaction
        BigDecimal newBalance = Optional.ofNullable(wallet.getBalance())
                .orElse(BigDecimal.ZERO)
                .add(request.getAmount());
        txn.setBalanceAfter(newBalance);

        // Reference for idempotency
        txn.setReference_id(request.getReference_id());

        // Status (could be SUCCESS, FAILED, PENDING)
        txn.setStatus("SUCCESS");

        // Description (optional)
        txn.setDescription("Wallet top-up via " + request.getProvider());

        // Audit timestamp
        txn.setCreatedAt(LocalDateTime.now());

        return txn;
    }

    private PaymentTransaction buildPaymentTxn(WalletTransaction walletTxn, WalletRequest request) {
        PaymentTransaction ptxn = new PaymentTransaction();
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Link to wallet transaction
        ptxn.setWalletTransaction(walletTxn);

        // Set provider details (from request)
        ptxn.setProvider(request.getProvider());           // e.g. "PAYPAL", "STRIPE"
        ptxn.setProvider_txn_id(request.getProvider_txn_id()); // external transaction ID
        ptxn.setPaymentTransactionUser(user);
        // Status (could be SUCCESS, PENDING, FAILED)
        ptxn.setStatus("SUCCESS"); // or derive from request.getStatus()

        // Payload (optional: raw JSON or metadata from provider)
        ptxn.setMeta(request.getPayload());
        ptxn.setMode(request.getMode());
        // Financial details
        ptxn.setAmount(request.getAmount());
        ptxn.setCurrency(request.getCurrency());

        // Reference for idempotency
        ptxn.setReferenceId(request.getReference_id());

        // Audit fields
        ptxn.setCreated_at(LocalDateTime.now());
        ptxn.setTxnDate(OffsetDateTime.now());
        ptxn.setType(request.getType());

        return ptxn;
    }

    @Transactional
    public Map<String, Object> withdrawAmount(WalletRequest request) {
        Map<String, Object> result = new LinkedHashMap<>();

        // Basic validation
        if (request == null || request.getUserId() == null) {
            result.put("success", false);
            result.put("message", "Request or userId cannot be null");
            return result;
        }
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            result.put("success", false);
            result.put("message", "Amount must be a positive value");
            return result;
        }

        Long userId = request.getUserId();
        try {
            // Obtain wallet with pessimistic lock; create if absent
            Wallet wallet = walletRepository.findByWalletUser_IdForUpdate(userId)
                    .orElseGet(() -> createWalletForUserWithLock(userId));

            // Idempotency: if reference provided and exists, return current state
            String reference = request.getReference_id();
            if (reference != null && !reference.isBlank() && paymentTransactionRepository.existsByReferenceId(reference)) {
                result.put("success", true);
                result.put("message", "Duplicate request: transaction already processed");
                result.put("wallet", mapToDto(wallet));
                return result;
            }

            // Check sufficient balance
            BigDecimal currentBalance = Optional.ofNullable(wallet.getBalance()).orElse(BigDecimal.ZERO);
            if (currentBalance.compareTo(request.getAmount()) < 0) {
                result.put("success", false);
                result.put("message", "Insufficient balance");
                result.put("wallet", mapToDto(wallet));
                return result;
            }

            // Update balance
            BigDecimal newBalance = currentBalance.subtract(request.getAmount());
            wallet.setBalance(newBalance);
            wallet = walletRepository.save(wallet);

            // Persist wallet transaction (ledger)
            WalletTransaction txn = buildTxn(wallet, request);
            // set type=DEBIT, amount, reference, balanceAfter=newBalance, createdAt etc.
            txn.setType("DEBIT");
            txn = walletTransactionRepository.save(txn);

            // Persist payment transaction linking to wallet transaction
            PaymentTransaction ptxn = buildPaymentTxn(txn, request);
            // set provider, providerTxnId, status, payload, amount, currency
            ptxn.setType("Withdrawal");
            paymentTransactionRepository.save(ptxn);

            // Compute blocked amount if applicable
            BigDecimal blocked = Optional.ofNullable(rentalRepo.sumBlockedForActiveRentalsByUserId(userId))
                    .map(b -> (BigDecimal) b)
                    .orElse(BigDecimal.ZERO);

            // Prepare response map
            result.put("success", true);
            result.put("message", "Amount withdrawn successfully");
            result.put("wallet", mapToDto(wallet));
            result.put("transactionId", txn.getId());
            result.put("paymentTransactionId", ptxn.getId());
            result.put("blocked", blocked);
            return result;

        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("success", false);
            result.put("message", "Failed to withdraw money: " + ex.getMessage());
            return result;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponse> getTransactions(Long userId, String filter) {
        List<String> types = switch (Optional.ofNullable(filter).orElse("ALL").toUpperCase()) {
            case "RENTALS" -> List.of("Rent Started", "Security Blocked", "Security Released", "Refund");
            case "DEPOSITS" -> List.of("Add Money", "Security Released", "Refund");
            case "WITHDRAWALS" -> List.of("Withdrawal");
            case "PENALTIES" -> List.of("Penalty");
            default -> null;
        };
        if (types == null) {
            return paymentTransactionRepository.findAllByUserId(userId);
        } else {
            return paymentTransactionRepository.findByPaymentTransactionUserIdAndType(userId, types);
        }
    }

    private WalletDto mapToDto(Wallet wallet) {
        WalletDto dto = new WalletDto();
        dto.setUserId(wallet.getWalletUser().getId());
        dto.setBalance(wallet.getBalance());
        return dto;
    }

    public WalletResponse mapToResponse(Wallet wallet, BigDecimal blocked) {
        BigDecimal balance = Optional.ofNullable(wallet.getBalance()).orElse(BigDecimal.ZERO);
        BigDecimal blockedAmount = Optional.ofNullable(blocked).orElse(BigDecimal.ZERO);
        BigDecimal available = balance.subtract(blockedAmount);
        if (available.compareTo(BigDecimal.ZERO) < 0) {
            available = BigDecimal.ZERO;
        }
        return new WalletResponse(balance, blockedAmount, available);
    }

    private Wallet createWalletForUserWithLock(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Wallet w = new Wallet();
        w.setWalletUser(user);
        w.setBalance(BigDecimal.ZERO);
        return walletRepository.save(w);
    }


}