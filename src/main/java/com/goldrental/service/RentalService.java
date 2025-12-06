package main.java.com.goldrental.service;

import main.java.com.goldrental.dto.RentalRequest;
import main.java.com.goldrental.dto.RentalDto;

import java.util.List;

public interface RentalService {

    RentalDto rentJewellery(RentalRequest request);

    RentalDto returnJewellery(Long rentalId);

    List<RentalDto> getUserRentals(Long userId);
}
