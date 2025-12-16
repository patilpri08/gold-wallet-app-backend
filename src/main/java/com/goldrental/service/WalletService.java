package com.goldrental.service;

import com.goldrental.dto.TransactionResponse;
import com.goldrental.dto.WalletRequest;
import com.goldrental.dto.WalletResponse;

import java.util.List;
import java.util.Map;

public interface WalletService {

    WalletResponse getSummary(Long userId);

    Map<String, Object> addMoney(WalletRequest request);

    Map<String, Object> withdrawAmount(WalletRequest request);

    List<TransactionResponse> getTransactions(Long userIdm, String filter);
}
