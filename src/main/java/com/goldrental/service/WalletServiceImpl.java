package com.goldrental.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.goldrental.dto.TransactionDto;
import com.goldrental.dto.WalletDto;
import com.goldrental.dto.WalletRequest;
import com.goldrental.dto.WalletResponse;
import com.goldrental.entity.User;
import com.goldrental.entity.Wallet;
import com.goldrental.repository.PaymentTransactionRepository;
import com.goldrental.repository.RentalRepository;
import com.goldrental.repository.UserRepository;
import com.goldrental.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final PaymentTransactionRepository txnRepo;
    private final RentalRepository rentalRepo;

    @Override
    @Transactional(readOnly = true)
    public WalletResponse getSummary(Long userId) {
        try {
            BigDecimal walletBalance = Optional.ofNullable(txnRepo.sumBalanceByUserId(userId)).orElse(BigDecimal.ZERO);
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

    @Override
    @Transactional
    public Map<String, Object> addMoney(WalletRequest request) {
        Map<String, Object> result = new LinkedHashMap<>();

        if (request == null || request.getUserId() == null) {
            result.put("success", false);
            result.put("message", "Request or userId cannot be null");
            return result;
        }

        BigDecimal amount = request.getAmount();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            result.put("success", false);
            result.put("message", "Amount must be a positive value");
            return result;
        }

        try {
            Wallet wallet = walletRepository.findByWalletUser_Id(request.getUserId());
            if (wallet == null) {
                User user = userRepository.findById(request.getUserId())
                        .orElse(null);
                if (user == null) {
                    result.put("success", false);
                    result.put("message", "User not found");
                    return result;
                }
                wallet = new Wallet();
                wallet.setWalletUser(user);
                wallet.setBalance(BigDecimal.ZERO);
            }

            wallet.setBalance(Optional.ofNullable(wallet.getBalance()).orElse(BigDecimal.ZERO).add(amount));
            wallet = walletRepository.save(wallet);

            result.put("success", true);
            result.put("message", "Amount added successfully");
            result.put("wallet", mapToDto(wallet)); // optional: include updated wallet DTO
            return result;
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", "Failed to add money: " + ex.getMessage());
            return result;
        }
    }

    @Override
    public Map<String, Object> withdrawAmount(WalletRequest request) {
        Map<String, Object> result = new LinkedHashMap<>();

        try {
            Wallet wallet = walletRepository.findByWalletUser_Id(request.getUserId());
            if (wallet == null) {
                result.put("success", false);
                result.put("message", "Wallet not found for user");
                return result;
            }

            BigDecimal amount = request.getAmount();
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                result.put("success", false);
                result.put("message", "Invalid withdraw amount");
                return result;
            }

            if (Optional.ofNullable(wallet.getBalance()).orElse(BigDecimal.ZERO).compareTo(amount) < 0) {
                result.put("success", false);
                result.put("message", "Insufficient balance");
                return result;
            }

            User user = userRepository.findById(request.getUserId())
                    .orElse(null);
            if (user == null) {
                result.put("success", false);
                result.put("message", "User not found");
                return result;
            }

            wallet.setWalletUser(user);
            wallet.setBalance(wallet.getBalance().subtract(amount));
            walletRepository.save(wallet);

            result.put("success", true);
            result.put("message", "Withdrawal successful");
            result.put("wallet", mapToDto(wallet)); // optional: include updated wallet DTO
            return result;
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", "Withdrawal failed: " + ex.getMessage());
            return result;
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<TransactionDto> getTransactions(Long userId, String filter) {
        List<String> types = switch (Optional.ofNullable(filter).orElse("ALL").toUpperCase()) {
            case "RENTALS" -> List.of("Rent Started", "Security Blocked", "Security Released", "Refund");
            case "DEPOSITS" -> List.of("Add Money", "Security Released", "Refund");
            case "WITHDRAWALS" -> List.of("Withdrawal");
            case "PENALTIES" -> List.of("Penalty");
            default -> null;
        };
        if (types == null) {
            return txnRepo.findAllByUserId(userId);
        } else {
            return txnRepo.findByUserIdAndTypes(userId, types);
        }
    }

    private WalletDto mapToDto(Wallet wallet) {
        WalletDto dto = new WalletDto();
        dto.setUserId(wallet.getWalletUser().getId());
        dto.setBalance(wallet.getBalance());
        return dto;
    }
}