package main.java.com.goldrental.service;

import main.java.com.goldrental.dto.WalletDto;
import main.java.com.goldrental.dto.WalletRequest;

public interface WalletService {

    WalletDto getWallet(Long userId);

    WalletDto addMoney(WalletRequest request);

    WalletDto withdrawAmount(WalletRequest request);

    Object getTransactions(Long userId);
}
