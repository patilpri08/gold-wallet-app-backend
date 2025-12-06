package com.goldrental.service;

import com.goldrental.dto.RentalRequest;
import com.goldrental.dto.RentalDto;

import java.util.List;

public interface RentalService {

    RentalDto rentJewellery(RentalRequest request);

    RentalDto returnJewellery(Long rentalId);

    List<RentalDto> getUserRentals(Long user_id);
}
