package main.java.com.goldrental.service;

import java.util.Map;

public interface DashboardService {

    Map<String, Object> getStats();

    Map<String, Object> getRevenueDetails();

    Map<String, Object> getRentalStats();
}
