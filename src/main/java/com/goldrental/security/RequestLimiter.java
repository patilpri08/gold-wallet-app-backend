package com.goldrental.security;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestLimiter {
    private static final int MAX_REQUESTS = 5;
    private static final ConcurrentHashMap<String, AtomicInteger> userRequests = new ConcurrentHashMap<>();

    public static boolean isAllowed(String userId) {
        AtomicInteger count = userRequests.computeIfAbsent(userId, k -> new AtomicInteger(0));
        if (count.incrementAndGet() > MAX_REQUESTS) {
            return false; // Block if more than 5
        }
        return true;
    }
}