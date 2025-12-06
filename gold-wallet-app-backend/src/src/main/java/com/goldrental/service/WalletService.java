package com.goldrental.service;

import com.goldrental.dto.WalletDto;
import com.goldrental.dto.WalletRequest;

public interface WalletService {

    WalletDto getWallet(Long userId);

    WalletDto addMoney(WalletRequest request);

    WalletDto withdrawAmount(WalletRequest request);

    Object getTransactions(Long userId);
}
