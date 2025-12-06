package com.goldrental.service;

import java.math.BigDecimal;

import lombok.RequiredArgsConstructor;
import com.goldrental.dto.WalletRequest;
import com.goldrental.repository.WalletRepository;
import com.goldrental.entity.Wallet;
import com.goldrental.dto.WalletDto;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public WalletDto getWallet(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId);
        return mapToDto(wallet);
    }

    @Override
    public WalletDto addMoney(WalletRequest request) {
        Wallet wallet = walletRepository.findByUserId(request.getUserId());
        wallet.setBalance(wallet.getBalance().add(request.getAmount()));
        walletRepository.save(wallet);
        return mapToDto(wallet);
    }

    @Override
    public WalletDto withdrawAmount(WalletRequest request) {
        Wallet wallet = walletRepository.findByUserId(request.getUserId());

        BigDecimal amount = request.getAmount();

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);

        return mapToDto(wallet);
    }

    @Override
    public Object getTransactions(Long userId) {
        return null;
    }

    private WalletDto mapToDto(Wallet wallet) {
        WalletDto dto = new WalletDto();
        dto.setUserId(wallet.getUserId());
        dto.setBalance(wallet.getBalance());
        return dto;
    }
}
